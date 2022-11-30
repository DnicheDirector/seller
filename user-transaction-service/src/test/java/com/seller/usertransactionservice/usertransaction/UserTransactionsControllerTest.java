package com.seller.usertransactionservice.usertransaction;

import com.seller.usertransactionservice.BaseTest;
import com.seller.usertransactionservice.usertransaction.models.UserTransaction;
import com.seller.usertransactionservice.usertransaction.services.UserTransactionService;
import com.seller.usertransactionservice.usertransaction.views.UserTransactionRequest;
import com.seller.usertransactionservice.usertransaction.views.UserTransactionResponse;
import com.seller.usertransactionservice.utils.ResponsePage;
import io.restassured.common.mapper.TypeRef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTransactionsControllerTest extends BaseTest {
    private static final String BASE_PATH = "/user-transactions";

    private static final Long positionId = 1L;
    private static final UUID userId = UUID.randomUUID();

    @Autowired
    private UserTransactionService userTransactionService;
    @Autowired
    private UserTransactionsMockTestHelper mockHelper;

    @Value("${pagination.max-page-size}")
    private int maxPageSize;

    @BeforeEach
    public void createCompany() {
        this.mockHelper.mockGetUser(userId);
        this.mockHelper.mockSubtractPositionAmount(positionId);
        for (int i = 0; i < 25; i++) {
            var userTransaction = UserTransaction.builder()
                    .positionId(positionId)
                    .amount(BigDecimal.valueOf(new Random().nextInt(1000)))
                    .created(ZonedDateTime.now())
                    .createdById(userId)
                    .build();
            userTransactionService.create(userTransaction);
        }
    }

    @Test
    public void successCreateUserTransaction() {
       var dto = UserTransactionRequest.builder()
               .amount(BigDecimal.valueOf(1000))
               .createdById(userId)
               .positionId(positionId)
               .build();
       var response = post(BASE_PATH, dto, HttpStatus.CREATED, UserTransactionResponse.class);

       assertNotNull(response.getId());
       assertNotNull(response.getCreated());
    }

    @Test
    public void getAllUserTransactionsWithoutPageParametersShouldReturnNoMoreThan20Records() {
        var response = get(
                String.format("%s?userId=%s", BASE_PATH, userId), HttpStatus.OK, new TypeRef<ResponsePage<UserTransactionResponse>>() {}
        );

        assertNotNull(response);
        assertEquals(maxPageSize, response.getContent().size());
    }

    @Test
    public void getAllUserTransactionsWithPageParametersShouldReturnNoMoreThanSelected() {
        var pageSize = 10;
        var response = get(
                String.format("%s?userId=%s&size=%s", BASE_PATH, userId, pageSize), HttpStatus.OK, new TypeRef<ResponsePage<UserTransactionResponse>>() {}
        );

        assertNotNull(response);
        assertEquals(pageSize, response.getContent().size());
    }

    @Test
    public void getAllUserTransactionsWithPageParametersShouldReturnNoMoreThan20Records() {
        var response = get(
                String.format("%s?userId=%s&size=%s", BASE_PATH, userId, 25), HttpStatus.OK, new TypeRef<ResponsePage<UserTransactionResponse>>() {}
        );

        assertNotNull(response);
        assertEquals(maxPageSize, response.getContent().size());
    }
}
