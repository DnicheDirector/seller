package com.company.seller.user.mappers;

import com.company.seller.user.models.User;
import com.company.seller.user.views.UserInputViewModel;
import com.company.seller.user.views.UserOutputViewModel;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(source = "user.company.id", target = "companyId")
  UserOutputViewModel toDto(User user);
  @Mapping(source = "user.company.id", target = "companyId")
  List<UserOutputViewModel> toDto(List<User> user);

  @Mapping(source = "dto.companyId", target = "company.id")
  User fromCreateDto(UserInputViewModel dto);
  @Mapping(source = "dto.companyId", target = "company.id")
  User fromUpdateDto(UserInputViewModel dto, UUID id);
}
