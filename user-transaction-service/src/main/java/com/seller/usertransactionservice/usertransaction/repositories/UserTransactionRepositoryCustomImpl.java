package com.seller.usertransactionservice.usertransaction.repositories;

import com.seller.usertransactionservice.usertransaction.models.UserTransaction;
import com.seller.usertransactionservice.usertransaction.models.UserTransactionStatus;
import com.seller.usertransactionservice.usertransaction.views.page.ResponsePage;
import com.seller.usertransactionservice.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserTransactionRepositoryCustomImpl implements UserTransactionRepositoryCustom {

    private final ReactiveMongoTemplate mongoTemplate;
    private final PaginationUtils paginationUtils;

    @Override
    public Mono<Void> updateUserTransactionStatus(String id, UserTransactionStatus status) {
        var clause = Query.query(
                Criteria.where("id").is(id)
        );
        var update = Update.update("status", status);
        return mongoTemplate.updateFirst(clause, update, UserTransaction.class).then();
    }

    @Override
    public Mono<ResponsePage<UserTransaction>> findAllByCreatedById(UUID userId, Pageable pageable) {
        var clause = Query.query(
                Criteria.where("createdById").is(userId)
        );
        return paginationUtils.toPage(clause, pageable, UserTransaction.class);
    }
}
