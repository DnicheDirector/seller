package com.company.seller.user.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.company.seller.BaseTest;
import com.company.seller.user.models.Role;
import com.company.seller.user.views.UserRequest;
import com.company.seller.user.views.UserResponse;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/clean-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsersControllerTest extends BaseTest {

  private static final String BASE_PATH = "/users";

  private Long companyId;
  private List<UUID> usersIds;

  @BeforeEach
  public void init() {
    companyId = testHelper.createRandomCompany().getId();

    var user1 = testHelper.createRandomUser();
    var user2 = testHelper.createRandomUser();
    usersIds = Arrays.asList(user1.getId(), user2.getId());
  }

  @Test
  public void createUserIfValidData() {
    var dto = UserRequest.builder()
        .name("Nick")
        .username("username1")
        .email("email@gmail.com")
        .role(Role.DIRECTOR.name())
        .companyId(companyId)
        .build();

    var result = post(BASE_PATH, dto, HttpStatus.CREATED, UserResponse.class);

    assertNotNull(result.getId());
    assertNotNull(result.getCreated());
    assertNotNull(result.getUpdated());
    assertEquals(dto.getName(), result.getName());
    assertEquals(dto.getRole(), result.getRole());
    assertEquals(dto.getCompanyId(), result.getCompanyId());
  }

  @Test
  public void createUserReturnsBadRequestIfInvalidData() {
    var dto = UserRequest.builder()
        .name("Kirill")
        .build();

    post(BASE_PATH, dto, HttpStatus.BAD_REQUEST);
  }

  @Test
  public void getUserIfExists() {
    var id = usersIds.get(0);
    var result = get(getPath(BASE_PATH, id), HttpStatus.OK, UserResponse.class);

    assertEquals(id, result.getId());
  }

  @Test
  public void getUserReturnsNotFoundIfDoesntExist() {
    get(getPath(BASE_PATH, "2eb43f59-19e3-4f17-9885-f5bc789f6cb6"), HttpStatus.NOT_FOUND);
  }

  @Test
  public void getAllUsers() {
    var result = get(BASE_PATH, HttpStatus.OK, UserResponse[].class);
    assertEquals(2, result.length);
  }

  @Test
  public void successUpdateUser() {
    var dto = UserRequest.builder()
        .name("Nick")
        .email("updated@gmail.com")
        .username("username2")
        .role(Role.MANAGER.name())
        .companyId(companyId)
        .build();

    var id = usersIds.get(0);

    var result = put(getPath(BASE_PATH, id), dto, HttpStatus.OK, UserResponse.class);

    assertNotNull(result.getCreated());
    assertNotNull(result.getUpdated());
    assertTrue(result.getUpdated().isAfter(result.getCreated()));
    assertEquals(id, result.getId());
    assertEquals(dto.getName(), result.getName());
    assertEquals(dto.getRole(), result.getRole());
  }
}
