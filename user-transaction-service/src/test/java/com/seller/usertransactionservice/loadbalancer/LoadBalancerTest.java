package com.seller.usertransactionservice.loadbalancer;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.seller.usertransactionservice.position.views.UpdatePositionAmountRequest;
import com.seller.usertransactionservice.sellersystem.client.SellerSystemClient;
import io.restassured.RestAssured;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.moreThanOrExactly;
import static com.github.tomakehurst.wiremock.client.WireMock.patchRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

@AutoConfigureWireMock(port = 0)
@TestPropertySource("classpath:load-balancer-application.yml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class LoadBalancerTest {

    @LocalServerPort
    private int port;

    private static final String BASE_POSITIONS_URL = "/api/positions";
    private static final String[] HEADER = { "Content-Type", "application/json" };

    private static final Long POSITION_ID = 10L;

    private static final int DELAY_AFTER_SWITCH = 8000;
    private static final int DELAY_AFTER_RETRY = 6000;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @RegisterExtension
    protected static WireMockExtension SellerSystemService = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(9002))
            .build();

    @Autowired
    private SellerSystemClient sellerSystemClient;

    @Test
    @SneakyThrows
    public void checkTwoInstances() {
        stubFor(WireMock.patch(urlEqualTo(getUpdatePositionAmountUrl()))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HEADER[0], HEADER[1])
                        .withFixedDelay(DELAY_AFTER_SWITCH)));
        SellerSystemService.stubFor(WireMock.patch(urlEqualTo(getUpdatePositionAmountUrl()))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HEADER[0], HEADER[1])));

        sellerSystemClient.updatePositionAmount(POSITION_ID, new UpdatePositionAmountRequest(BigDecimal.ONE)).block();
        verify(moreThanOrExactly(1), patchRequestedFor(urlEqualTo(getUpdatePositionAmountUrl())));
    }


    @Test
    public void checkRetryAfterTimeout() {
        stubFor(WireMock.patch(urlEqualTo(getUpdatePositionAmountUrl()))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HEADER[0], HEADER[1])
                        .withFixedDelay(DELAY_AFTER_RETRY)));

        sellerSystemClient.updatePositionAmount(POSITION_ID, new UpdatePositionAmountRequest(BigDecimal.ONE)).block();
        verify(moreThanOrExactly(1), patchRequestedFor(urlEqualTo(getUpdatePositionAmountUrl())));
    }

    public String getUpdatePositionAmountUrl() {
        return String.format("%s/%s/amount", BASE_POSITIONS_URL, POSITION_ID);
    }
}
