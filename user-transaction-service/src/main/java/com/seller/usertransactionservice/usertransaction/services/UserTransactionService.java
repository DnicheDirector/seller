package com.seller.usertransactionservice.usertransaction.services;

import com.seller.usertransactionservice.sellersystem.client.SellerSystemClient;
import com.seller.usertransactionservice.usertransaction.configuration.PaginationProps;
import com.seller.usertransactionservice.usertransaction.exceptions.UserTransactionException;
import com.seller.usertransactionservice.usertransaction.models.UserTransactionStatus;
import com.seller.usertransactionservice.usertransaction.models.UserTransaction;
import com.seller.usertransactionservice.usertransaction.producers.PositionProducer;
import com.seller.usertransactionservice.usertransaction.repositories.UserTransactionRepository;
import com.seller.usertransactionservice.usertransaction.views.page.ResponsePage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.ZonedDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTransactionService {

    private final UserTransactionRepository userTransactionRepository;
    private final PaginationProps paginationProps;
    private final SellerSystemClient sellerSystemClient;
    private final PositionProducer positionProducer;

    @Transactional
    public Mono<UserTransaction> create(UserTransaction userTransaction) {
        log.info("Creating user transaction : {}", userTransaction);
        return sellerSystemClient.getUserById(userTransaction.getCreatedById())
                .flatMap(userResponse -> sellerSystemClient.getPositionById(userTransaction.getPositionId()))
                .flatMap(position -> {
                    if (position.getAmount().compareTo(userTransaction.getAmount()) < 0) {
                        return Mono.error(
                                new UserTransactionException(position.getAmount(), userTransaction.getAmount())
                        );
                    }
                    userTransaction.setCreated(ZonedDateTime.now());
                    userTransaction.setStatus(UserTransactionStatus.IN_PROGRESS);
                    return userTransactionRepository.save(userTransaction);
                })
                .doOnNext(positionProducer::sendReducePositionAmountMessage)
                .onErrorMap(error -> new UserTransactionException(error.getMessage()));
    }

    public Mono<ResponsePage<UserTransaction>> getAll(UUID userId, Integer page, Integer size) {
        var pageNumber = page != null
                ? page
                : 0;
        var pageSize = size == null || size > paginationProps.getMaxPageSize()
                ? paginationProps.getMaxPageSize()
                : size;
        return this.userTransactionRepository.findAllByCreatedById(
                userId, PageRequest.of(pageNumber, pageSize)
        );
    }

    @Transactional
    public Mono<Void> updateUserTransactionStatus(String id, UserTransactionStatus status) {
        return userTransactionRepository.updateUserTransactionStatus(id, status);
    }
}
