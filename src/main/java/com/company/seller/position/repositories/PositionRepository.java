package com.company.seller.position.repositories;

import com.company.seller.position.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> { }
