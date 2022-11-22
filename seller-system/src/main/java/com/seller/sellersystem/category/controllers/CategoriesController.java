package com.seller.sellersystem.category.controllers;

import com.seller.sellersystem.category.mappers.CategoryMapper;
import com.seller.sellersystem.category.services.CategoryService;
import com.seller.sellersystem.category.views.CategoryRequest;
import com.seller.sellersystem.category.views.CategoryResponse;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CategoriesController {

  private final CategoryService categoryService;
  private final CategoryMapper categoryMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CategoryResponse createCategory(
      @RequestBody @Valid CategoryRequest dto
  ) {
    var category = categoryMapper.fromCreateDto(dto);
    return categoryMapper.toDto(
        categoryService.create(category)
    );
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public CategoryResponse getCategory(@PathVariable Long id) {
    return categoryMapper.toDto(
        categoryService.getById(id)
    );
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CategoryResponse> getAll() {
    return categoryMapper.toDto(
        categoryService.getAll()
    );
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public CategoryResponse updateCategory(
      @PathVariable Long id, @RequestBody @Valid CategoryRequest dto
  ) {
    var category = categoryMapper.fromUpdateDto(dto, id);
    return categoryMapper.toDto(
        categoryService.update(id, category)
    );
  }

}
