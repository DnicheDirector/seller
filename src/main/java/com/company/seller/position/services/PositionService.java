package com.company.seller.position.services;

import com.company.seller.position.models.Position;
import com.company.seller.position.repositories.PositionRepository;
import com.company.seller.services.AbstractCrudService;
import java.time.ZonedDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionService extends AbstractCrudService<PositionRepository, Position, Long> {

  public PositionService(PositionRepository repository) {
    super(repository);
  }

  @Override
  @Transactional
  public Position create(Position position) {
    position.setCreated(ZonedDateTime.now());
    return super.create(position);
  }

  @Override
  @Transactional
  public Position update(Long id, Position position) {
    var existing = getById(id);
    position.setCreated(existing.getCreated());
    return repository.save(position);
  }
}
