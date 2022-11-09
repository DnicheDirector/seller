package com.company.seller.user.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.company.seller.BaseTest;
import com.company.seller.user.models.Role;
import com.company.seller.user.views.UserInputViewModel;
import com.company.seller.user.views.UserOutputViewModel;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/user/create-users-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clean-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsersControllerTest extends BaseTest {

  private static final String BASE_PATH = "/users";

  private static final Long COMPANY_ID = 100L;
  private static final UUID[] IDS = {
      UUID.fromString("fd422f25-7e5e-4b90-9d5f-cc835aa0900f"),
      UUID.fromString("2d422f23-7e5e-4b90-9d5f-cc835aa0900f")
  };

  @Test
  public void createUserIfValidData() throws Exception {
    var dto = UserInputViewModel.builder()
        .name("Nick")
        .username("username1")
        .created(LocalDateTime.now())
        .updated(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
        .email("email@gmail.com")
        .role(Role.DIRECTOR.name())
        .companyId(COMPANY_ID)
        .build();

    var response = post(BASE_PATH, dto)
        .andExpect(status().isCreated())
        .andReturn().getResponse();
    var body = getBody(response, UserOutputViewModel.class);

    assertNotNull(body.getId());
    assertEquals(dto.getName(), body.getName());
    assertEquals(dto.getRole(), body.getRole());
    assertEquals(dto.getCompanyId(), body.getCompanyId());
  }

  @Test
  public void createUserReturnsBadRequestIfInvalidData() throws Exception {
    var dto = UserInputViewModel.builder()
        .name("Kirill")
        .build();

    post(BASE_PATH, dto)
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getUserIfExists() throws Exception {
    var id = IDS[0];
    var response = get(getPath(BASE_PATH, id))
        .andExpect(status().isOk())
        .andReturn().getResponse();

    var body = getBody(response, UserOutputViewModel.class);

    assertEquals(id, body.getId());
  }

  @Test
  public void getUserReturnsNotFoundIfDoesntExist() throws Exception {
    get(getPath(BASE_PATH, "2eb43f59-19e3-4f17-9885-f5bc789f6cb6"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getAllUsers() throws Exception {
    get(BASE_PATH)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  public void successUpdateUser() throws Exception {
    var dto = UserInputViewModel.builder()
        .name("Nick")
        .created(LocalDateTime.now())
        .updated(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
        .email("updated@gmail.com")
        .username("username2")
        .role(Role.MANAGER.name())
        .companyId(COMPANY_ID)
        .build();

    var id = IDS[0];

    var response = put(getPath(BASE_PATH, id), dto)
        .andExpect(status().isOk())
        .andReturn().getResponse();
    var body = getBody(response, UserOutputViewModel.class);

    assertEquals(id, body.getId());
    assertEquals(dto.getName(), body.getName());
    assertEquals(dto.getCreated(), body.getCreated());
    assertEquals(dto.getRole(), body.getRole());
  }
}
