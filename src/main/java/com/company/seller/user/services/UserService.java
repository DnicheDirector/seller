package com.company.seller.user.services;

import com.company.seller.user.models.User;
import com.company.seller.user.repositories.UserRepository;
import com.company.seller.services.AbstractCrudService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractCrudService<UserRepository, User, UUID> {

  public UserService(UserRepository repository) {
    super(repository);
  }
}
