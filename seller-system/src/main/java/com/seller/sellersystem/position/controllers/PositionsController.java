package com.seller.sellersystem.position.controllers;

import com.seller.sellersystem.position.mappers.PositionMapper;
import com.seller.sellersystem.position.services.PositionService;
import com.seller.sellersystem.position.views.PositionRequest;
import com.seller.sellersystem.position.views.PositionResponse;
import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("positions")
@RequiredArgsConstructor
public class PositionsController {

  private final PositionService positionService;
  private final PositionMapper positionMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PositionResponse createPosition(
      @RequestBody @Valid PositionRequest dto
  ) {
    var position = positionMapper.fromCreateDto(dto);
    return positionMapper.toDto(
        positionService.create(position)
    );
  }

  @GetMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public PositionResponse getPosition(@PathVariable Long id) {
    return positionMapper.toDto(
        positionService.getById(id)
    );
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<PositionResponse> getPosition() {
    return positionMapper.toDto(
        positionService.getAll()
    );
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.OK)
  public PositionResponse updatePosition(
      @PathVariable Long id, @RequestBody @Valid PositionRequest dto
  ) {
    var position = positionMapper.fromUpdateDto(dto, id);
    return positionMapper.toDto(
        positionService.update(id, position)
    );
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePosition(@PathVariable Long id) {
    positionService.delete(id);
  }
}
