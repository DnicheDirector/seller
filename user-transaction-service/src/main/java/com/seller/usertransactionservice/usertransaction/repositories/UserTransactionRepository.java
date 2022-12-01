package com.seller.usertransactionservice.usertransaction.repositories;

import com.seller.usertransactionservice.usertransaction.models.UserTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {
    Page<UserTransaction> findAllByCreatedById(UUID userId, Pageable pageable);
}
