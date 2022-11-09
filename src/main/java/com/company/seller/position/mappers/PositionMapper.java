package com.company.seller.position.mappers;

import com.company.seller.position.models.Position;
import com.company.seller.position.views.PositionInputViewModel;
import com.company.seller.position.views.PositionOutputViewModel;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PositionMapper {
  @Mapping(source = "position.item.id", target = "itemId")
  @Mapping(source = "position.company.id", target = "companyId")
  @Mapping(source = "position.createdBy.id", target = "createdById")
  PositionOutputViewModel toDto(Position position);

  @Mapping(source = "position.item.id", target = "itemId")
  @Mapping(source = "position.company.id", target = "companyId")
  @Mapping(source = "position.createdBy.id", target = "createdById")
  List<PositionOutputViewModel> toDto(List<Position> position);

  @Mapping(source = "dto.itemId", target = "item.id")
  @Mapping(source = "dto.companyId", target = "company.id")
  @Mapping(source = "dto.createdById", target = "createdBy.id")
  Position fromCreateDto(PositionInputViewModel dto);

  @Mapping(source = "dto.itemId", target = "item.id")
  @Mapping(source = "dto.companyId", target = "company.id")
  @Mapping(source = "dto.createdById", target = "createdBy.id")
  Position fromUpdateDto(PositionInputViewModel dto, Long id);
}
