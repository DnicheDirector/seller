package com.company.seller.category.mappers;

import com.company.seller.category.models.Category;
import com.company.seller.category.views.CategoryInputViewModel;
import com.company.seller.category.views.CategoryOutputViewModel;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
  @Mapping(source = "category.parentCategory.id", target = "parentCategoryId")
  CategoryOutputViewModel toDto(Category category);

  @Mapping(source = "category.parentCategory.id", target = "parentCategoryId")
  List<CategoryOutputViewModel> toDto(List<Category> category);

  @Mapping(source = "dto.parentCategoryId", target = "parentCategory.id")
  Category fromCreateDto(CategoryInputViewModel dto);
  @Mapping(source = "dto.parentCategoryId", target = "parentCategory.id")
  Category fromUpdateDto(CategoryInputViewModel dto, Long id);

  @AfterMapping
  default void afterMappingFromDto(@MappingTarget Category category) {
    if (category.getParentCategory() != null && category.getParentCategory().getId() == null) {
      category.setParentCategory(null);
    }
  }
}