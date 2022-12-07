package com.seller.usertransactionservice.usertransaction;

import com.seller.usertransactionservice.BaseTest;
import com.seller.usertransactionservice.usertransaction.kafka.KafkaTestConsumer;
import com.seller.usertransactionservice.usertransaction.kafka.KafkaTestProducer;
import com.seller.usertransactionservice.usertransaction.models.UserTransaction;
import com.seller.usertransactionservice.usertransaction.models.UserTransactionStatus;
import com.seller.usertransactionservice.usertransaction.repositories.UserTransactionRepository;
import com.seller.usertransactionservice.usertransaction.views.page.ResponsePage;
import com.seller.usertransactionservice.usertransaction.views.user.UserTransactionRequest;
import com.seller.usertransactionservice.usertransaction.views.user.UserTransactionResponse;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rnorth.ducttape.unreliables.Unreliables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserTransactionsControllerTest extends BaseTest {
    private static final String BASE_PATH = "/user-transactions";

    private static final Long POSITION_ID = 1L;
    private static final UUID USER_ID = UUID.randomUUID();

    private UserTransaction createdUserTransaction;

    @SpyBean
    private UserTransactionRepository userTransactionRepository;
    @Autowired
    private UserTransactionsMockTestHelper mockHelper;
    @Autowired
    private KafkaTestConsumer kafkaTestConsumer;
    @Autowired
    private KafkaTestProducer kafkaTestProducer;

    @Value("${pagination.max-page-size}")
    private int maxPageSize;

    @BeforeEach
    public void createUserTransaction() {
        this.mockHelper.mockGetUser(USER_ID);
        this.mockHelper.mockGetPositionById(POSITION_ID, USER_ID, BigDecimal.valueOf(2000));
        for (int i = 0; i < 25; i++) {
            var userTransaction = UserTransaction.builder()
                    .positionId(POSITION_ID)
                    .amount(BigDecimal.valueOf(new Random().nextInt(1000)))
                    .created(ZonedDateTime.now())
                    .createdById(USER_ID)
                    .status(UserTransactionStatus.IN_PROGRESS)
                    .build();
            userTransactionRepository.save(userTransaction);
            this.createdUserTransaction = userTransaction;
        }
    }

    @AfterEach
    public void reset() {
        kafkaTestConsumer.resetLatch();
    }

    @Test
    public void successCreateUserTransaction() throws InterruptedException {
       var dto = UserTransactionRequest.builder()
               .amount(BigDecimal.valueOf(1000))
               .createdById(USER_ID)
               .positionId(POSITION_ID)
               .build();
       var response = post(BASE_PATH, dto, HttpStatus.CREATED, UserTransactionResponse.class);

       assertNotNull(response.getId());
       assertNotNull(response.getCreated());
       assertEquals(UserTransactionStatus.IN_PROGRESS, response.getStatus());

       var messageConsumed = kafkaTestConsumer.getLatch().await(BASE_TIMEOUT, TimeUnit.SECONDS);
       assertTrue(messageConsumed);
       assertEquals(response.getId(), kafkaTestConsumer.getConsumedMessage().getUserTransactionId());
    }

    @Test
    public void createUserTransactionReturnsBadRequestIfInsufficientAmount() {
        var dto = UserTransactionRequest.builder()
                .amount(BigDecimal.valueOf(3000))
                .createdById(USER_ID)
                .positionId(POSITION_ID)
                .build();
        post(BASE_PATH, dto, HttpStatus.BAD_REQUEST);
    }


    @Test
    public void successUpdateUserTransactionStatus() {
        assertEquals(UserTransactionStatus.IN_PROGRESS, createdUserTransaction.getStatus());
        kafkaTestProducer.sendUserTransactionStatusMessage(
                createdUserTransaction.getId(), createdUserTransaction.getCreatedById(), UserTransactionStatus.SUCCESS
        );
        Unreliables.retryUntilTrue(BASE_TIMEOUT, TimeUnit.SECONDS, () -> {
            var updatedUserTransaction = userTransactionRepository.findById(createdUserTransaction.getId());
            return updatedUserTransaction.stream().allMatch(u -> u.getStatus() == UserTransactionStatus.SUCCESS);
        });
        verify(userTransactionRepository, times(1))
                .updateUserTransactionStatus(createdUserTransaction.getId(), UserTransactionStatus.SUCCESS);
    }

    @Test
    public void getAllUserTransactionsWithoutPageParametersShouldReturnNoMoreThan20Records() {
        var response = get(
                String.format("%s?userId=%s", BASE_PATH, USER_ID), HttpStatus.OK, new TypeRef<ResponsePage<UserTransactionResponse>>() {}
        );

        assertNotNull(response);
        assertEquals(maxPageSize, response.getContent().size());
    }

    @Test
    public void getAllUserTransactionsWithPageParametersShouldReturnNoMoreThanSelected() {
        var pageSize = 10;
        var response = get(
                String.format("%s?userId=%s&size=%s", BASE_PATH, USER_ID, pageSize), HttpStatus.OK, new TypeRef<ResponsePage<UserTransactionResponse>>() {}
        );

        assertNotNull(response);
        assertEquals(pageSize, response.getContent().size());
    }

    @Test
    public void getAllUserTransactionsWithPageParametersShouldReturnNoMoreThan20Records() {
        var response = get(
                String.format("%s?userId=%s&size=%s", BASE_PATH, USER_ID, 25), HttpStatus.OK, new TypeRef<ResponsePage<UserTransactionResponse>>() {
                }
        );

        assertNotNull(response);
        assertEquals(maxPageSize, response.getContent().size());
    }
}
