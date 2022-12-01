package com.seller.sellersystem.position.services;

import com.seller.sellersystem.position.exception.UpdatePositionAmountException;
import com.seller.sellersystem.position.models.Position;
import com.seller.sellersystem.position.repositories.PositionRepository;
import com.seller.sellersystem.services.AbstractCrudService;

import java.math.BigDecimal;
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

  @Transactional
  public Position subtractPositionAmount(Long id, BigDecimal requiredAmount) {
    var existing = repository.getPositionByIdForUpdate(id);
    var currentAmount = existing.getAmount();
    if (currentAmount.compareTo(requiredAmount) > 0) {
      existing.setAmount(currentAmount.subtract(requiredAmount));
    } else {
      throw new UpdatePositionAmountException(currentAmount, requiredAmount);
    }
    return existing;
  }
}
