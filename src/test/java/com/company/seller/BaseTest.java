package com.company.seller;

import static io.restassured.RestAssured.given;

import com.company.seller.containers.PostgreSQLTestContainer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class BaseTest {

  @LocalServerPort
  private int port;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
  }

  @Container
  public static PostgreSQLTestContainer postgreSQLContainer = PostgreSQLTestContainer.getInstance();

  @Autowired
  protected TestHelper testHelper;

  @DynamicPropertySource
  public static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
  }

  protected <T> T get(String path, HttpStatus expectedStatusCode, Class<T> classType) {
    return get(path, expectedStatusCode)
        .extract()
        .as(classType);
  }

  protected ValidatableResponse get(String path, HttpStatus expectedStatusCode) {
    return RestAssured.get(path)
        .then()
        .assertThat()
        .statusCode(expectedStatusCode.value());
  }

  protected <T> T put(String path, Object body, HttpStatus expectedStatusCode, Class<T> classType) {
    return put(path, body, expectedStatusCode)
        .extract()
        .as(classType);
  }

  protected ValidatableResponse put(String path, Object body, HttpStatus expectedStatusCode) {
    return given()
        .body(body)
        .contentType(ContentType.JSON)
        .when()
        .put(path)
        .then()
        .assertThat()
        .statusCode(expectedStatusCode.value());
  }

  protected <T> T post(String path, Object body, HttpStatus expectedStatusCode, Class<T> classType) {
    return post(path, body, expectedStatusCode)
        .extract()
        .as(classType);
  }

  protected ValidatableResponse post(String path, Object body, HttpStatus expectedStatusCode) {
    return given()
        .body(body)
        .contentType(ContentType.JSON)
        .when()
        .post(path)
        .then()
        .assertThat()
        .statusCode(expectedStatusCode.value());
  }

  protected void delete(String path, HttpStatus expectedStatusCode) {
    RestAssured.delete(path)
        .then()
        .assertThat()
        .statusCode(expectedStatusCode.value());
  }

  protected <ID> String getPath(String path, ID id) {
    return String.format("%s/%s", path, id);
  }

}
