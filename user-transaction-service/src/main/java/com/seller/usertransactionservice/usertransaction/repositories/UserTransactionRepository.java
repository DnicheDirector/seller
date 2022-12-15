package com.seller.usertransactionservice.usertransaction.repositories;

import com.seller.usertransactionservice.usertransaction.models.UserTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserTransactionRepository extends
        ReactiveMongoRepository<UserTransaction, String>, UserTransactionRepositoryCustom { }
