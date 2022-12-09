package com.seller.usertransactionservice;

import com.seller.usertransactionservice.containers.KafkaTestContainer;
import com.seller.usertransactionservice.containers.PostgreSQLTestContainer;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@Testcontainers
public class BaseTest {

  protected static final int BASE_TIMEOUT = 10;

  @LocalServerPort
  private int port;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
  }

  @Container
  public static PostgreSQLTestContainer postgreSQLContainer = PostgreSQLTestContainer.getInstance();

  @Container
  public static KafkaTestContainer kafkaTestContainer = KafkaTestContainer.getInstance();

  @DynamicPropertySource
  public static void overrideProperties(DynamicPropertyRegistry registry) {
    postgreSQLContainer.addTestContainerProperties(registry);
    kafkaTestContainer.addTestContainersProperties(registry);
  }

  protected <T> T get(String path, HttpStatus expectedStatusCode, TypeRef<T> typeRef) {
    return get(path, expectedStatusCode)
            .extract()
            .as(typeRef);
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

}
