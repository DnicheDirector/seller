package com.company.seller.user.repositories;

import com.company.seller.user.models.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> { }
