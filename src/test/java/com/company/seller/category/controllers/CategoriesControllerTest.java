package com.company.seller.category.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.company.seller.BaseTest;
import com.company.seller.category.views.CategoryRequest;
import com.company.seller.category.views.CategoryResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;


@Sql(scripts = "/clean-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CategoriesControllerTest extends BaseTest {

  private static final String BASE_PATH = "/categories";

  private List<Long> ids;

  @BeforeEach
  public void init() {
    var category1 = testHelper.createRandomCategory();
    var category2 = testHelper.createRandomCategory();
    ids = Arrays.asList(category1.getId(), category2.getId());
  }

  @Test
  public void createCategoryIfValidData() {
    var dto = CategoryRequest.builder()
        .name("category1")
        .build();

    var result = post(BASE_PATH, dto, HttpStatus.CREATED, CategoryResponse.class);

    assertNotNull(result.getId());
    assertEquals(dto.getName(), result.getName());
  }

  @Test
  public void createCategoryReturnsBadRequestIfInvalidData() {
    var dto = CategoryRequest.builder()
        .description("description")
        .build();

    post(BASE_PATH, dto, HttpStatus.BAD_REQUEST);
  }

  @Test
  public void getCategoryIfExists() {
    var id = ids.get(0);
    var result = get(getPath(BASE_PATH, id), HttpStatus.OK, CategoryResponse.class);

    assertEquals(id, result.getId());
  }

  @Test
  public void getCategoryReturnsNotFoundIfDoesntExist() {
    get(getPath(BASE_PATH, 1000L), HttpStatus.NOT_FOUND);
  }

  @Test
  public void getAllCategories() {
    var result = get(BASE_PATH, HttpStatus.OK, CategoryResponse[].class);
    assertEquals(2, result.length);
  }

  @Test
  public void successUpdateCategory() {
    var id = ids.get(1);

    var dto = CategoryRequest.builder()
        .name("updatedCategory")
        .parentCategoryId(ids.get(0))
        .build();

    var result = put(getPath(BASE_PATH, id), dto, HttpStatus.OK, CategoryResponse.class);

    assertEquals(id, result.getId());
    assertEquals(dto.getParentCategoryId(), result.getParentCategoryId());
  }
}
