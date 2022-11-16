package com.company.seller.category.mappers;

import com.company.seller.category.models.Category;
import com.company.seller.category.views.CategoryRequest;
import com.company.seller.category.views.CategoryResponse;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface CategoryMapper {
  @Mapping(source = "category.parentCategory.id", target = "parentCategoryId")
  CategoryResponse toDto(Category category);

  @Mapping(source = "category.parentCategory.id", target = "parentCategoryId")
  List<CategoryResponse> toDto(List<Category> category);

  @Mapping(source = "dto.parentCategoryId", target = "parentCategory.id")
  Category fromCreateDto(CategoryRequest dto);
  @Mapping(source = "dto.parentCategoryId", target = "parentCategory.id")
  Category fromUpdateDto(CategoryRequest dto, Long id);

  @AfterMapping
  default void afterMappingFromDto(@MappingTarget Category category) {
    if (category.getParentCategory() != null && category.getParentCategory().getId() == null) {
      category.setParentCategory(null);
    }
  }
}