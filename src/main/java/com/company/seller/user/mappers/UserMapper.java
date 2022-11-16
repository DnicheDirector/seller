package com.company.seller.user.mappers;

import com.company.seller.user.models.User;
import com.company.seller.user.views.UserRequest;
import com.company.seller.user.views.UserResponse;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
  @Mapping(source = "user.company.id", target = "companyId")
  UserResponse toDto(User user);
  @Mapping(source = "user.company.id", target = "companyId")
  List<UserResponse> toDto(List<User> user);

  @Mapping(source = "dto.companyId", target = "company.id")
  User fromCreateDto(UserRequest dto);
  @Mapping(source = "dto.companyId", target = "company.id")
  User fromUpdateDto(UserRequest dto, UUID id);
}
