package com.seller.sellersystem.company.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.seller.sellersystem.company.views.CompanyRequest;
import com.seller.sellersystem.company.views.CompanyResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Service
@RequiredArgsConstructor
public class CompaniesMockTestHelper {
    private static final Long MOCK_ID = 100L;
    private static final String BASE_PATH = "/api/companies";
    private static final String[] HEADER = { "Content-Type", "application/json" };

    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void mockCompanyCreation(CompanyRequest request) {
        var body = objectMapper.writeValueAsString(
                createCompanyResponse(request)
        );
        stubFor(WireMock.post(urlEqualTo(BASE_PATH)).willReturn(
                        aResponse()
                                .withStatus(HttpStatus.CREATED.value())
                                .withHeader(HEADER[0], HEADER[1])
                                .withBody(body)
                )
        );
    }

    @SneakyThrows
    public void mockCompanyUpdate(Long id, CompanyRequest request) {
        var body = objectMapper.writeValueAsString(
                createCompanyResponse(request)
        );
        stubFor(WireMock.put(urlEqualTo(String.format("%s/%s",BASE_PATH, id))).willReturn(
                        aResponse()
                                .withStatus(HttpStatus.CREATED.value())
                                .withHeader(HEADER[0], HEADER[1])
                                .withBody(body)
                )
        );
    }

    @SneakyThrows
    public void mockGetById(Long id) {
        var body = objectMapper.writeValueAsString(
                createCompanyResponse()
        );
        stubFor(WireMock.get(urlEqualTo(String.format("%s/%s", BASE_PATH, id))).willReturn(
                        aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HEADER[0], HEADER[1])
                                .withBody(body)
                )
        );
    }

    @SneakyThrows
    public void mockGetAll() {
        var body = objectMapper.writeValueAsString(
                Collections.singletonList(createCompanyResponse())
        );
        stubFor(WireMock.get(urlEqualTo(BASE_PATH)).willReturn(
                        aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HEADER[0], HEADER[1])
                                .withBody(body)
                )
        );
    }

    private CompanyResponse createCompanyResponse(CompanyRequest request) {
        return CompanyResponse.builder()
                .id(MOCK_ID)
                .name(request.getName())
                .created(ZonedDateTime.now())
                .email(request.getEmail())
                .description(request.getDescription())
                .build();
    }

    private CompanyResponse createCompanyResponse() {
        return CompanyResponse.builder()
                .id(MOCK_ID)
                .name("Dniche COmpany")
                .created(ZonedDateTime.now())
                .email("dniche@gmail.com")
                .description("description")
                .build();
    }
}
