package com.company.seller.company.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.company.seller.BaseTest;
import com.company.seller.company.views.CompanyInputViewModel;
import com.company.seller.company.views.CompanyOutputViewModel;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/company/create-company-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clean-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CompaniesControllerTest extends BaseTest {

  private static final Long COMPANY_ID = 100L;
  private static final String BASE_PATH = "/companies";

  @Test
  public void createCompanyIfValidData() throws Exception {
    var dto = CompanyInputViewModel.builder()
        .name("company1")
        .email("example@gmail.com")
        .created(LocalDateTime.now())
        .description("123")
        .build();

    var response = post(BASE_PATH, dto)
            .andExpect(status().isCreated())
            .andReturn().getResponse();
    var body = getBody(response, CompanyOutputViewModel.class);

    assertNotNull(body.getId());
    assertEquals(dto.getName(), body.getName());
  }

  @Test
  public void createCompanyReturnsBadRequestIfInvalidData() throws Exception {
    var dto = CompanyInputViewModel.builder()
        .name("company1")
        .build();

    post(BASE_PATH, dto)
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getCompanyIfExists() throws Exception {
    var response = get(getPath(BASE_PATH, COMPANY_ID))
        .andExpect(status().isOk())
        .andReturn().getResponse();

    var body = getBody(response, CompanyOutputViewModel.class);

    assertEquals(COMPANY_ID, body.getId());
  }

  @Test
  public void getCompanyReturnsNotFoundIfDoesntExist() throws Exception {
    get(getPath(BASE_PATH, 1000L))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getAllCompanies() throws Exception {
    get(BASE_PATH)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  public void successUpdateCompany() throws Exception {
    var dto = CompanyInputViewModel.builder()
        .name("updatedCompany")
        .email("updated@gmail.com")
        .created(LocalDateTime.now())
        .description("newDescription")
        .build();

    var response = put(getPath(BASE_PATH, COMPANY_ID), dto)
        .andExpect(status().isOk())
        .andReturn().getResponse();
    var body = getBody(response, CompanyOutputViewModel.class);

    assertEquals(COMPANY_ID, body.getId());
    assertEquals(dto.getEmail(), body.getEmail());
    assertEquals(dto.getName(), body.getName());
    assertEquals(dto.getDescription(), body.getDescription());
    assertEquals(dto.getCreated(), body.getCreated());
  }
}
