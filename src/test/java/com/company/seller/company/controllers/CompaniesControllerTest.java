package com.company.seller.company.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.company.seller.BaseTest;
import com.company.seller.company.views.CompanyRequest;
import com.company.seller.company.views.CompanyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/clean-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CompaniesControllerTest extends BaseTest {

  private static final String BASE_PATH = "/companies";

  private Long companyId;

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
    var result = get(getPath(BASE_PATH, companyId), HttpStatus.OK, CompanyResponse.class);
    assertEquals(companyId, result.getId());
  }

  @Test
  public void getCompanyReturnsNotFoundIfDoesntExist() {
    get(getPath(BASE_PATH, 1000L), HttpStatus.NOT_FOUND);
  }

  @Test
  public void getAllCompanies() {
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

    var result = put(getPath(BASE_PATH, companyId), dto, HttpStatus.OK, CompanyResponse.class);

    assertNotNull(result.getCreated());
    assertEquals(companyId, result.getId());
    assertEquals(dto.getEmail(), result.getEmail());
    assertEquals(dto.getName(), result.getName());
    assertEquals(dto.getDescription(), result.getDescription());
  }
}
