package com.company.seller.user.controllers;

import com.company.seller.user.mappers.UserMapper;
import com.company.seller.user.services.UserService;
import com.company.seller.user.views.UserInputViewModel;
import com.company.seller.user.views.UserOutputViewModel;
import java.util.List;
import java.util.UUID;
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
@RequiredArgsConstructor
@RequestMapping("users")
public class UsersController {

  private final UserService userService;
  private final UserMapper userMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserOutputViewModel createUser(
      @RequestBody @Valid UserInputViewModel dto
  ) {
    var user = userMapper.fromCreateDto(dto);
    return userMapper.toDto(
        userService.create(user)
    );
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserOutputViewModel getUser(@PathVariable UUID id) {
    return userMapper.toDto(
        userService.getById(id)
    );
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<UserOutputViewModel> getUser() {
    return userMapper.toDto(
        userService.getAll()
    );
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserOutputViewModel updateUser(
      @PathVariable UUID id, @RequestBody  @Valid UserInputViewModel dto
  ) {
    var user = userMapper.fromUpdateDto(dto, id);
    return userMapper.toDto(
        userService.update(id, user)
    );
  }

}
