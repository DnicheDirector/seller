package com.seller.sellersystem.item.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.seller.sellersystem.BaseTest;
import com.seller.sellersystem.item.views.ItemRequest;
import com.seller.sellersystem.item.views.ItemResponse;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/clean-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ItemsControllerTest extends BaseTest {

  private static final String BASE_PATH = "/items";

  private Long categoryId;
  private List<UUID> ids;

  @BeforeEach
  public void init() {
    var item1 = testHelper.createRandomItem();
    var item2 = testHelper.createRandomItem();
    ids = Arrays.asList(item1.getId(), item2.getId());
    categoryId = testHelper.createRandomCategory().getId();
  }

  @Test
  public void createItemIfValidData() {
    var dto = ItemRequest.builder()
        .name("item1")
        .categoryId(categoryId)
        .build();

    var result = post(BASE_PATH, dto, HttpStatus.CREATED, ItemResponse.class);

    assertNotNull(result.getId());
    assertEquals(dto.getName(), result.getName());
  }

  @Test
  public void createItemReturnsBadRequestIfInvalidData() {
    var dto = ItemRequest.builder()
        .name("randomName")
        .build();

    post(BASE_PATH, dto, HttpStatus.BAD_REQUEST);
  }

  @Test
  public void getItemIfExists() {
    var id = ids.get(0);
    var result = get(getPath(BASE_PATH, id), HttpStatus.OK, ItemResponse.class);

    assertEquals(id, result.getId());
  }

  @Test
  public void getItemReturnsNotFoundIfDoesntExist() {
    get(getPath(BASE_PATH, "2eb43f59-19e3-4f17-9885-f5bc789f6cb6"), HttpStatus.NOT_FOUND);
  }

  @Test
  public void getAllItem() {
    var result = get(BASE_PATH, HttpStatus.OK, ItemResponse[].class);
    assertEquals(2, result.length);
  }

  @Test
  public void successUpdateItem() {
    var dto = ItemRequest.builder()
        .name("updatedItem")
        .categoryId(categoryId)
        .build();

    var id = ids.get(0);

    var result = put(getPath(BASE_PATH, id), dto, HttpStatus.OK, ItemResponse.class);

    assertEquals(id, result.getId());
    assertEquals(dto.getName(), result.getName());
    assertNotNull(result.getCreated());
  }

}
