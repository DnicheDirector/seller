package com.company.seller.position.services;

import com.company.seller.position.models.Position;
import com.company.seller.position.repositories.PositionRepository;
import com.company.seller.services.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class PositionService extends AbstractCrudService<PositionRepository, Position, Long> {

  public PositionService(PositionRepository repository) {
    super(repository);
  }
}
