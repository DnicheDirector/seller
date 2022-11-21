package com.seller.sellersystem.user.repositories;

import com.seller.sellersystem.user.models.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> { }
