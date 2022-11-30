package com.seller.usertransactionservice.usertransaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.seller.usertransactionservice.usertransaction.models.UserTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@Service
@RequiredArgsConstructor
public class UserTransactionsMockTestHelper {
    private static final String BASE_POSITIONS_PATH = "/api/positions";
    private static final String BASE_USERS_PATH = "/api/users";
    private static final String[] HEADER = { "Content-Type", "application/json" };

    public void mockGetUser(UUID userId) {
        stubFor(WireMock.get(urlEqualTo(String.format("%s/%s", BASE_USERS_PATH, userId))).willReturn(
                        aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HEADER[0], HEADER[1])
                )
        );
    }

    public void mockSubtractPositionAmount(Long positionId) {
        stubFor(WireMock.patch(urlEqualTo(String.format("%s/%s/amount", BASE_POSITIONS_PATH, positionId))).willReturn(
                        aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HEADER[0], HEADER[1])
                )
        );
    }

}
