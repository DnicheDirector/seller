package com.seller.sellersystem.position.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.seller.sellersystem.BaseTest;
import com.seller.sellersystem.position.views.PositionRequest;
import com.seller.sellersystem.position.views.PositionResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/clean-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PositionsControllerTest extends BaseTest {

  private static final String BASE_PATH = "/positions";

  private Long companyId;
  private UUID userId;
  private List<Long> positionsIds;
  private List<UUID> itemsIds;

  @BeforeEach
  public void init() {
    companyId = testHelper.createRandomCompany().getId();

    var item1 = testHelper.createRandomItem();
    var item2 = testHelper.createRandomItem();
    itemsIds = Arrays.asList(item1.getId(), item2.getId());

    userId = testHelper.createRandomUser().getId();

    var position1 = testHelper.createRandomPosition();
    var position2 = testHelper.createRandomPosition();

    positionsIds = Arrays.asList(position1.getId(), position2.getId());
  }

  @Test
  public void createPositionIfValidData() {
    var dto = PositionRequest.builder()
        .createdById(userId)
        .amount(BigDecimal.valueOf(500))
        .companyId(companyId)
        .itemId(itemsIds.get(0))
        .build();

    var result = post(BASE_PATH, dto, HttpStatus.CREATED, PositionResponse.class);

    assertNotNull(result.getId());
    assertNotNull(result.getCreated());
    assertEquals(dto.getItemId(), result.getItemId());
    assertEquals(dto.getCreatedById(), result.getCreatedById());
    assertEquals(dto.getCompanyId(), result.getCompanyId());
    assertEquals(dto.getAmount(), result.getAmount());
  }

  @Test
  public void createPositionReturnsBadRequestIfInvalidData() {
    var dto = PositionRequest.builder()
        .createdById(userId)
        .build();

    post(BASE_PATH, dto, HttpStatus.BAD_REQUEST);
  }

  @Test
  public void getPositionIfExists() {
    var id = positionsIds.get(0);
    var result = get(getPath(BASE_PATH, id), HttpStatus.OK, PositionResponse.class);

    assertEquals(id, result.getId());
  }

  @Test
  public void getPositionReturnsNotFoundIfDoesntExist() {
    get(getPath(BASE_PATH, 1000L), HttpStatus.NOT_FOUND);
  }

  @Test
  public void getAllPositions() {
    getAll(2);
  }

  @Test
  public void successUpdatePosition() {
    var dto = PositionRequest.builder()
        .createdById(userId)
        .amount(BigDecimal.valueOf(1000))
        .companyId(companyId)
        .itemId(itemsIds.get(1))
        .build();

    var id = positionsIds.get(0);

    var result = put(getPath(BASE_PATH, id), dto, HttpStatus.OK, PositionResponse.class);

    assertNotNull(result.getCreated());
    assertEquals(id, result.getId());
    assertEquals(dto.getItemId(), result.getItemId());
    assertEquals(dto.getCreatedById(), result.getCreatedById());
    assertEquals(dto.getCompanyId(), result.getCompanyId());
    assertEquals(dto.getAmount(), result.getAmount());
  }

  @Test
  public void successDelete() {
    getAll(2);
    delete(getPath(BASE_PATH, positionsIds.get(0)), HttpStatus.NO_CONTENT);
    getAll(1);
  }

  private void getAll(int expectedSize) {
    var result = get(BASE_PATH, HttpStatus.OK, PositionResponse[].class);
    assertEquals(expectedSize, result.length);
  }
}
