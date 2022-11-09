package com.company.seller.category.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.company.seller.BaseTest;
import com.company.seller.category.views.CategoryInputViewModel;
import com.company.seller.category.views.CategoryOutputViewModel;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;


@Sql(scripts = "/category/create-categories-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clean-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CategoriesControllerTest extends BaseTest {

  private static final String BASE_PATH = "/categories";
  private static final Long[] IDS = {10L, 11L};

  @Test
  public void createCategoryIfValidData() throws Exception {
    var dto = CategoryInputViewModel.builder()
        .name("category1")
        .build();

    var response = post(BASE_PATH, dto)
        .andExpect(status().isCreated())
        .andReturn().getResponse();
    var body = getBody(response, CategoryOutputViewModel.class);

    assertNotNull(body.getId());
    assertEquals(dto.getName(), body.getName());
  }

  @Test
  public void createCategoryReturnsBadRequestIfInvalidData() throws Exception {
    var dto = CategoryInputViewModel.builder()
        .build();

    post(BASE_PATH, dto)
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getCategoryIfExists() throws Exception {
    var id = IDS[0];
    var response = get(getPath(BASE_PATH, id))
        .andExpect(status().isOk())
        .andReturn().getResponse();

    var body = getBody(response, CategoryOutputViewModel.class);

    assertEquals(id, body.getId());
  }

  @Test
  public void getCategoryReturnsNotFoundIfDoesntExist() throws Exception {
    get(getPath(BASE_PATH, 1000L))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getAllCategories() throws Exception {
    get(BASE_PATH)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  public void successUpdateCategory() throws Exception {
    var id = IDS[1];

    var dto = CategoryInputViewModel.builder()
        .name("updatedCategory")
        .parentCategoryId(IDS[0])
        .build();

    var response = put(getPath(BASE_PATH, id), dto)
        .andExpect(status().isOk())
        .andReturn().getResponse();
    var body = getBody(response, CategoryOutputViewModel.class);

    assertEquals(id, body.getId());
    assertEquals(dto.getParentCategoryId(), body.getParentCategoryId());
  }
}
