package com.seller.sellersystem.user.mappers;

import com.seller.sellersystem.user.models.User;
import com.seller.sellersystem.user.views.UserRequest;
import com.seller.sellersystem.user.views.UserResponse;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface UserMapper {
  UserResponse toDto(User user);
  List<UserResponse> toDto(List<User> user);

  User fromCreateDto(UserRequest dto);
  User fromUpdateDto(UserRequest dto, UUID id);
}
