package com.seller.sellersystem.position.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.seller.sellersystem.BaseTest;
import com.seller.sellersystem.position.controllers.kafka.KafkaTestConsumer;
import com.seller.sellersystem.position.controllers.kafka.KafkaTestProducer;
import com.seller.sellersystem.position.models.Position;
import com.seller.sellersystem.position.repositories.PositionRepository;
import com.seller.sellersystem.position.views.PositionRequest;
import com.seller.sellersystem.position.views.PositionResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.seller.sellersystem.usertransaction.messages.UserTransactionStatus;
import com.seller.sellersystem.usertransaction.messages.UserTransactionStatusMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rnorth.ducttape.unreliables.Unreliables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Sql(scripts = "/clean-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PositionsControllerTest extends BaseTest {

  private static final String BASE_PATH = "/positions";

  private Long companyId;
  private UUID userId;
  private List<Position> positions;
  private List<UUID> itemsIds;

  @Autowired
  private KafkaTestConsumer kafkaTestConsumer;
  @Autowired
  private KafkaTestProducer kafkaTestProducer;
  @SpyBean
  private PositionRepository positionRepository;

  @BeforeEach
  public void init() {
    companyId = testHelper.createRandomCompany().getId();

    var item1 = testHelper.createRandomItem();
    var item2 = testHelper.createRandomItem();
    itemsIds = Arrays.asList(item1.getId(), item2.getId());

    userId = testHelper.createRandomUser().getId();

    positions = Arrays.asList(
            testHelper.createRandomPosition(BigDecimal.valueOf(100)),
            testHelper.createRandomPosition(BigDecimal.valueOf(55))
    );
  }

  @AfterEach
  public void reset() {
    kafkaTestConsumer.reset();
  }

  @Test
  public void createPositionIfValidData() {
    var dto = PositionRequest.builder()
        .createdById(userId)
        .amount(BigDecimal.valueOf(500))
        .companyId(companyId)
        .itemId(itemsIds.get(0))
        .build();

    var result = post(BASE_PATH, dto, HttpStatus.CREATED, PositionResponse.class);

    assertNotNull(result.getId());
    assertNotNull(result.getCreated());
    assertEquals(dto.getItemId(), result.getItemId());
    assertEquals(dto.getCreatedById(), result.getCreatedById());
    assertEquals(dto.getCompanyId(), result.getCompanyId());
    assertEquals(dto.getAmount(), result.getAmount());
  }

  @Test
  public void createPositionReturnsBadRequestIfInvalidData() {
    var dto = PositionRequest.builder()
        .createdById(userId)
        .build();

    post(BASE_PATH, dto, HttpStatus.BAD_REQUEST);
  }

  @Test
  public void getPositionIfExists() {
    var id = positions.get(0).getId();
    var result = getPosition(id);

    assertEquals(id, result.getId());
  }

  @Test
  public void getPositionReturnsNotFoundIfDoesntExist() {
    get(getPath(BASE_PATH, 1000L), HttpStatus.NOT_FOUND);
  }

  @Test
  public void getAllPositions() {
    getAll(2);
  }

  @Test
  public void successUpdatePosition() {
    var dto = PositionRequest.builder()
        .createdById(userId)
        .amount(BigDecimal.valueOf(1000))
        .companyId(companyId)
        .itemId(itemsIds.get(1))
        .build();

    var id = positions.get(0).getId();

    var result = put(getPath(BASE_PATH, id), dto, HttpStatus.OK, PositionResponse.class);

    assertNotNull(result.getCreated());
    assertEquals(id, result.getId());
    assertEquals(dto.getItemId(), result.getItemId());
    assertEquals(dto.getCreatedById(), result.getCreatedById());
    assertEquals(dto.getCompanyId(), result.getCompanyId());
    assertEquals(dto.getAmount(), result.getAmount());
  }

  @Test
  public void successUpdatePositionAmount() throws InterruptedException {
    var amountForSubtract = BigDecimal.valueOf(40);
    var position = positions.get(0);

    var userTransactionId = 1L;

    kafkaTestProducer.sendReducePositionAmountMessage(userTransactionId, userId, position.getId(), amountForSubtract);

    Unreliables.retryUntilTrue(BASE_TIMEOUT, TimeUnit.SECONDS, () -> {
      var updatedPosition = positionRepository.findById(position.getId());
      return updatedPosition.stream().allMatch(p ->
              position.getAmount().subtract(amountForSubtract).compareTo(p.getAmount()) == 0
      );
    });
    var messageReceived = kafkaTestConsumer.getLatch().await(BASE_TIMEOUT, TimeUnit.SECONDS);

    assertTrue(messageReceived);

    assertEquals(userTransactionId, kafkaTestConsumer.getConsumedMessages().get(0).getUserTransactionId());
    assertEquals(UserTransactionStatus.SUCCESS, kafkaTestConsumer.getConsumedMessages().get(0).getStatus());

    verify(positionRepository, times(1))
            .getPositionByIdForUpdate(position.getId());

    var result = getPosition(position.getId());

    assertEquals(position.getId(), result.getId());
    assertEquals(0, position.getAmount().subtract(amountForSubtract).compareTo(result.getAmount()));
  }

  @Test
  public void successUpdatePositionAmountIfConcurrentTransactions() throws InterruptedException {
    var amountForSubtract1 = BigDecimal.valueOf(40);
    var amountForSubtract2 = BigDecimal.valueOf(50);

    var userTransactionId1 = 1L;
    var userTransactionId2 = 2L;

    var position = positions.get(0);

    kafkaTestConsumer.setLatch(2);

    kafkaTestProducer.sendReducePositionAmountMessage(userTransactionId1, userId, position.getId(), amountForSubtract1);
    kafkaTestProducer.sendReducePositionAmountMessage(userTransactionId2, userId, position.getId(), amountForSubtract2);

    Unreliables.retryUntilTrue(BASE_TIMEOUT, TimeUnit.SECONDS, () -> {
      var updatedPosition = positionRepository.findById(position.getId());
      return updatedPosition.stream().allMatch(p ->
              position.getAmount().subtract(amountForSubtract1).subtract(amountForSubtract2).compareTo(p.getAmount()) == 0
      );
    });
    var messageConsumed = kafkaTestConsumer.getLatch().await(BASE_TIMEOUT, TimeUnit.SECONDS);

    var userTransactionStatusMessage1 = new UserTransactionStatusMessage(userTransactionId1, UserTransactionStatus.SUCCESS);
    var userTransactionStatusMessage2 = new UserTransactionStatusMessage(userTransactionId2, UserTransactionStatus.SUCCESS);

    assertTrue(messageConsumed);

    assertEquals(2, kafkaTestConsumer.getConsumedMessages().size());
    assertEquals(Arrays.asList(userTransactionStatusMessage1, userTransactionStatusMessage2), kafkaTestConsumer.getConsumedMessages());

    verify(positionRepository, times(2))
            .getPositionByIdForUpdate(position.getId());

    var updatedPosition = getPosition(position.getId());

    assertEquals(position.getId(), updatedPosition.getId());
    assertEquals(0, position.getAmount()
            .subtract(amountForSubtract1)
            .subtract(amountForSubtract2)
            .compareTo(updatedPosition.getAmount())
    );
  }

  @Test
  public void updatePositionAmountSetErrorStatusIfInsufficientAmount() throws InterruptedException {
    var amountForSubtract = BigDecimal.valueOf(120);
    var userTransactionId = 1L;

    var position = positions.get(0);

    kafkaTestProducer.sendReducePositionAmountMessage(userTransactionId, userId, position.getId(), amountForSubtract);

    var messageConsumed = kafkaTestConsumer.getLatch().await(BASE_TIMEOUT, TimeUnit.SECONDS);
    var actualPosition = getPosition(position.getId());

    assertTrue(messageConsumed);
    assertEquals(1, kafkaTestConsumer.getConsumedMessages().size());
    assertEquals(0, position.getAmount().compareTo(actualPosition.getAmount()));
    assertEquals(userTransactionId, kafkaTestConsumer.getConsumedMessages().get(0).getUserTransactionId());
    assertEquals(UserTransactionStatus.ERROR, kafkaTestConsumer.getConsumedMessages().get(0).getStatus());
  }

  @Test
  public void successDelete() {
    getAll(2);
    delete(getPath(BASE_PATH, positions.get(0).getId()), HttpStatus.NO_CONTENT);
    getAll(1);
  }

  private void getAll(int expectedSize) {
    var result = get(BASE_PATH, HttpStatus.OK, PositionResponse[].class);
    assertEquals(expectedSize, result.length);
  }

  private PositionResponse getPosition(Long id) {
    return get(getPath(BASE_PATH, id), HttpStatus.OK, PositionResponse.class);
  }
}
