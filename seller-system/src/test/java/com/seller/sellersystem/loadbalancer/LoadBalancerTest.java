package com.seller.sellersystem.loadbalancer;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.seller.sellersystem.company.client.CompanyClient;
import com.seller.sellersystem.containers.PostgreSQLTestContainer;
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
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.moreThanOrExactly;
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

    private static final String BASE_URL = "/api/companies";
    private static final String[] HEADER = { "Content-Type", "application/json" };

    private static final int DELAY_AFTER_SWITCH = 8000;
    private static final int DELAY_AFTER_RETRY = 6000;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Container
    public static PostgreSQLTestContainer postgreSQLContainer = PostgreSQLTestContainer.getInstance();

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        postgreSQLContainer.addTestContainerProperties(registry);
    }

    @RegisterExtension
    protected static WireMockExtension CompanyService = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(9002))
            .build();

    @Autowired
    private CompanyClient companyClient;

    @Test
    @SneakyThrows
    public void checkTwoInstances() {
        stubFor(WireMock.get(urlEqualTo(BASE_URL))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HEADER[0], HEADER[1])
                        .withFixedDelay(DELAY_AFTER_SWITCH)));
        CompanyService.stubFor(WireMock.get(urlEqualTo(BASE_URL))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HEADER[0], HEADER[1])));

        companyClient.getAll();
        verify(moreThanOrExactly(1), getRequestedFor(urlEqualTo(BASE_URL)));
    }


    @Test
    public void checkRetryAfterTimeout() {
        stubFor(WireMock.get(urlEqualTo(BASE_URL))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HEADER[0], HEADER[1])
                        .withFixedDelay(DELAY_AFTER_RETRY)));

        companyClient.getAll();
        verify(moreThanOrExactly(1), getRequestedFor(urlEqualTo(BASE_URL)));
    }


}
