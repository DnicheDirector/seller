package com.seller.sellersystem.position.repositories;

import com.seller.sellersystem.position.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> { }
