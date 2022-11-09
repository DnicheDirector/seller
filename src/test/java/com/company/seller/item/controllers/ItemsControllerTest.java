package com.company.seller.item.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.company.seller.BaseTest;
import com.company.seller.item.views.ItemInputViewModel;
import com.company.seller.item.views.ItemOutputViewModel;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/item/create-items-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clean-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ItemsControllerTest extends BaseTest {

  private static final String BASE_PATH = "/items";

  private static final Long CATEGORY_ID = 10L;
  private static final UUID[] IDS = {
      UUID.fromString("b812e9a3-9c8f-46cd-ae79-cabecded7852"),
      UUID.fromString("fd422f25-7e5e-4b90-9d5f-cc835aa0900f")
  };

  @Test
  public void createItemIfValidData() throws Exception {
    var dto = ItemInputViewModel.builder()
        .name("item1")
        .created(LocalDateTime.now())
        .categoryId(CATEGORY_ID)
        .build();

    var response = post(BASE_PATH, dto)
        .andExpect(status().isCreated())
        .andReturn().getResponse();
    var body = getBody(response, ItemOutputViewModel.class);

    assertNotNull(body.getId());
    assertEquals(dto.getName(), body.getName());
  }

  @Test
  public void createItemReturnsBadRequestIfInvalidData() throws Exception {
    var dto = ItemInputViewModel.builder()
        .name("randomName")
        .build();

    post(BASE_PATH, dto)
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getItemIfExists() throws Exception {
    var id = IDS[0];
    var response = get(getPath(BASE_PATH, id))
        .andExpect(status().isOk())
        .andReturn().getResponse();

    var body = getBody(response, ItemOutputViewModel.class);

    assertEquals(id, body.getId());
  }

  @Test
  public void getItemReturnsNotFoundIfDoesntExist() throws Exception {
    get(getPath(BASE_PATH, "2eb43f59-19e3-4f17-9885-f5bc789f6cb6"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getAllItem() throws Exception {
    get(BASE_PATH)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  public void successUpdateItem() throws Exception {
    var dto = ItemInputViewModel.builder()
        .name("updatedItem")
        .created(LocalDateTime.now())
        .categoryId(CATEGORY_ID)
        .build();

    var id = IDS[0];

    var response = put(getPath(BASE_PATH, id), dto)
        .andExpect(status().isOk())
        .andReturn().getResponse();
    var body = getBody(response, ItemOutputViewModel.class);

    assertEquals(id, body.getId());
    assertEquals(dto.getName(), body.getName());
    assertEquals(dto.getCreated(), body.getCreated());
  }

}
