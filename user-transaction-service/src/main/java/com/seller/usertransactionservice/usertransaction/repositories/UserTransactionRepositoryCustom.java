package com.seller.usertransactionservice.usertransaction.repositories;

import com.seller.usertransactionservice.usertransaction.models.UserTransaction;
import com.seller.usertransactionservice.usertransaction.models.UserTransactionStatus;
import com.seller.usertransactionservice.usertransaction.views.page.ResponsePage;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserTransactionRepositoryCustom {
    Mono<Void> updateUserTransactionStatus(String id, UserTransactionStatus status);
    Mono<ResponsePage<UserTransaction>> findAllByCreatedById(UUID id, Pageable pageable);
}
