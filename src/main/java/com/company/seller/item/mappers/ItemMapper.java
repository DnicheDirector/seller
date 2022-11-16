package com.company.seller.item.mappers;

import com.company.seller.item.models.Item;
import com.company.seller.item.views.ItemRequest;
import com.company.seller.item.views.ItemResponse;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
  @Mapping(source = "item.category.id", target = "categoryId")
  ItemResponse toDto(Item item);
  @Mapping(source = "item.category.id", target = "categoryId")
  List<ItemResponse> toDto(List<Item> item);

  @Mapping(source = "dto.categoryId", target = "category.id")
  Item fromCreateDto(ItemRequest dto);
  @Mapping(source = "dto.categoryId", target = "category.id")
  Item fromUpdateDto(ItemRequest dto, UUID id);
}
