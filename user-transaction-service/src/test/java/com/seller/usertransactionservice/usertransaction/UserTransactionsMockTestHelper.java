package com.seller.usertransactionservice.usertransaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.seller.usertransactionservice.position.views.PositionResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
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

    private final ObjectMapper objectMapper;


    public void mockGetUser(UUID userId) {
        stubFor(WireMock.get(urlEqualTo(String.format("%s/%s", BASE_USERS_PATH, userId))).willReturn(
                        aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HEADER[0], HEADER[1])
                )
        );
    }

    @SneakyThrows
    public void mockGetPositionById(Long id, UUID userId, BigDecimal amount) {
        var dto = PositionResponse.builder()
                .id(id)
                .amount(amount)
                .created(ZonedDateTime.now())
                .companyId(1L)
                .createdById(userId)
                .itemId(UUID.randomUUID())
                .build();
        stubFor(WireMock.get(urlEqualTo(String.format("%s/%s", BASE_POSITIONS_PATH, id))).willReturn(
                        aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HEADER[0], HEADER[1])
                                .withBody(objectMapper.writeValueAsString(dto))
                )
        );
    }

}
