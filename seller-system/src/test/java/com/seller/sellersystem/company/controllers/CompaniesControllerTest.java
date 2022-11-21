package com.seller.sellersystem.company.controllers;

import com.seller.sellersystem.BaseTest;
import com.seller.sellersystem.company.views.CompanyRequest;
import com.seller.sellersystem.company.views.CompanyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql(scripts = "/clean-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CompaniesControllerTest extends BaseTest {

    private static final String BASE_PATH = "/companies";

    private Long companyId;

    @Autowired
    private CompaniesMockTestHelper companiesMockTestHelper;

    @BeforeEach
    public void createCompany() {
        companyId = testHelper.createRandomCompany().getId();
    }

    @Test
    public void createCompanyIfValidData() {
        var dto = CompanyRequest.builder()
                .name("company1")
                .email("example@gmail.com")
                .description("123")
                .build();

        companiesMockTestHelper.mockCompanyCreation(dto);

        var result = post(BASE_PATH, dto, HttpStatus.CREATED, CompanyResponse.class);

        assertNotNull(result.getId());
        assertEquals(dto.getName(), result.getName());
    }

    @Test
    public void createCompanyReturnsBadRequestIfInvalidData() {
        var dto = CompanyRequest.builder()
                .name("company1")
                .build();

        post(BASE_PATH, dto, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void getCompanyIfExists() {
        companiesMockTestHelper.mockGetById(companyId, testHelper.createRandomCompany());
        var result = get(getPath(BASE_PATH, companyId), HttpStatus.OK, CompanyResponse.class);
        assertEquals(companyId, result.getId());
    }

    @Test
    public void getAllCompanies() {
        companiesMockTestHelper.mockGetAll(testHelper.createRandomCompany());
        var result = get(BASE_PATH, HttpStatus.OK, CompanyResponse[].class);
        assertEquals(1, result.length);
    }

    @Test
    public void successUpdateCompany() {
        var dto = CompanyRequest.builder()
                .name("updatedCompany")
                .email("updated@gmail.com")
                .description("newDescription")
                .build();

        companiesMockTestHelper.mockCompanyUpdate(companyId, dto);

        var result = put(getPath(BASE_PATH, companyId), dto, HttpStatus.OK, CompanyResponse.class);

        assertNotNull(result.getCreated());
        assertEquals(companyId, result.getId());
        assertEquals(dto.getEmail(), result.getEmail());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getDescription(), result.getDescription());
    }
}
