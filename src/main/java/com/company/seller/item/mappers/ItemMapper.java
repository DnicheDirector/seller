package com.company.seller.item.mappers;

import com.company.seller.item.models.Item;
import com.company.seller.item.views.ItemInputViewModel;
import com.company.seller.item.views.ItemOutputViewModel;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
  @Mapping(source = "item.category.id", target = "categoryId")
  ItemOutputViewModel toDto(Item item);
  @Mapping(source = "item.category.id", target = "categoryId")
  List<ItemOutputViewModel> toDto(List<Item> item);

  @Mapping(source = "dto.categoryId", target = "category.id")
  Item fromCreateDto(ItemInputViewModel dto);
  @Mapping(source = "dto.categoryId", target = "category.id")
  Item fromUpdateDto(ItemInputViewModel dto, UUID id);
}
