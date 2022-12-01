package com.seller.sellersystem.position.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.seller.sellersystem.BaseTest;
import com.seller.sellersystem.position.models.Position;
import com.seller.sellersystem.position.views.PositionRequest;
import com.seller.sellersystem.position.views.PositionResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.seller.sellersystem.position.views.UpdatePositionAmountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/clean-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PositionsControllerTest extends BaseTest {

  private static final String BASE_PATH = "/positions";

  private Long companyId;
  private UUID userId;
  private List<Position> positions;
  private List<UUID> itemsIds;

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
  public void successUpdatePositionAmount() {
    var amountForSubtract = BigDecimal.valueOf(40);
    var position = positions.get(0);

    var result = updatePositionAmount(position.getId(), amountForSubtract);

    assertEquals(position.getId(), result.getId());
    assertEquals(0, position.getAmount().subtract(amountForSubtract).compareTo(result.getAmount()));
  }

  @Test
  public void successUpdatePositionAmountIfConcurrentTransactions() throws InterruptedException {
    var amountForSubtract1 = BigDecimal.valueOf(40);
    var amountForSubtract2 = BigDecimal.valueOf(50);

    var position = positions.get(0);

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    executorService.submit(() -> updatePositionAmount(position.getId(), amountForSubtract1));
    executorService.submit(() -> updatePositionAmount(position.getId(), amountForSubtract2));

    executorService.shutdown();
    var executed = executorService.awaitTermination(1, TimeUnit.MINUTES);

    var updatedPosition = getPosition(position.getId());

    assertTrue(executed);
    assertEquals(position.getId(), updatedPosition.getId());
    assertEquals(0, position.getAmount()
            .subtract(amountForSubtract1)
            .subtract(amountForSubtract2)
            .compareTo(updatedPosition.getAmount())
    );
  }

  @Test
  public void updatePositionAmountReturnsBadRequestIfInsufficientAmount() {
    var dto = new UpdatePositionAmountRequest(BigDecimal.valueOf(120));

    var position = positions.get(0);

    patch(String.format("%s/amount", getPath(BASE_PATH, position.getId())), dto, HttpStatus.BAD_REQUEST);
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

  private PositionResponse updatePositionAmount(Long id, BigDecimal amountForSubtract) {
    var dto = new UpdatePositionAmountRequest(amountForSubtract);
    return patch(
            String.format("%s/amount", getPath(BASE_PATH, id)),
            dto, HttpStatus.OK, PositionResponse.class
    );
  }
}
