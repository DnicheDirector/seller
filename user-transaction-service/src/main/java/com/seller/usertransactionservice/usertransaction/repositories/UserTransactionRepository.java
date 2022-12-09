package com.seller.usertransactionservice.usertransaction.repositories;

import com.seller.usertransactionservice.usertransaction.models.UserTransactionStatus;
import com.seller.usertransactionservice.usertransaction.models.UserTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {
    Page<UserTransaction> findAllByCreatedById(UUID userId, Pageable pageable);

    @Modifying
    @Query("update UserTransaction ut set ut.status = :status where ut.id = :id")
    void updateUserTransactionStatus(Long id, UserTransactionStatus status);
}
