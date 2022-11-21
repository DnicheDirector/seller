package com.seller.sellersystem.user.services;

import com.seller.sellersystem.user.models.User;
import com.seller.sellersystem.user.repositories.UserRepository;
import com.seller.sellersystem.services.AbstractCrudService;
import java.time.ZonedDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService extends AbstractCrudService<UserRepository, User, UUID> {

  public UserService(UserRepository repository) {
    super(repository);
  }

  @Override
  @Transactional
  public User create(User user) {
    ZonedDateTime now = ZonedDateTime.now();
    user.setCreated(now);
    user.setUpdated(now);
    return super.create(user);
  }

  @Override
  @Transactional
  public User update(UUID id, User user) {
    var existing = getById(id);
    user.setCreated(existing.getCreated());
    user.setUpdated(ZonedDateTime.now());
    return this.repository.save(user);
  }
}
