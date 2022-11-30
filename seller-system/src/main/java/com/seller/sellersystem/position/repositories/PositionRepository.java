package com.seller.sellersystem.position.repositories;

import com.seller.sellersystem.position.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;

public interface PositionRepository extends JpaRepository<Position, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Position p where p.id = :id")
    Position getPositionByIdForUpdate(Long id);
}
