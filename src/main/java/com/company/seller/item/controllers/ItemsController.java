package com.company.seller.item.controllers;

import com.company.seller.item.mappers.ItemMapper;
import com.company.seller.item.services.ItemService;
import com.company.seller.item.views.ItemInputViewModel;
import com.company.seller.item.views.ItemOutputViewModel;
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
@RequestMapping("items")
@RequiredArgsConstructor
public class ItemsController {

  private final ItemService itemService;
  private final ItemMapper itemMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ItemOutputViewModel createItem(
      @RequestBody @Valid ItemInputViewModel dto
  ) {
    var item = itemMapper.fromCreateDto(dto);
    return itemMapper.toDto(
        itemService.create(item)
    );
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public ItemOutputViewModel getItem(@PathVariable UUID id) {
    return itemMapper.toDto(
        itemService.getById(id)
    );
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ItemOutputViewModel> getItem() {
    return itemMapper.toDto(
        itemService.getAll()
    );
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public ItemOutputViewModel updateItem(
      @PathVariable UUID id, @RequestBody @Valid ItemInputViewModel dto
  ) {
    var item = itemMapper.fromUpdateDto(dto, id);
    return itemMapper.toDto(
        itemService.update(id, item)
    );
  }
}
