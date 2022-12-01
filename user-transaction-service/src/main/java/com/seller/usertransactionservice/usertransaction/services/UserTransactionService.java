package com.seller.usertransactionservice.usertransaction.services;

import com.seller.usertransactionservice.sellersystem.client.SellerSystemClient;
import com.seller.usertransactionservice.position.views.UpdatePositionAmountRequest;
import com.seller.usertransactionservice.usertransaction.configuration.PaginationProps;
import com.seller.usertransactionservice.usertransaction.models.UserTransaction;
import com.seller.usertransactionservice.usertransaction.repositories.UserTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserTransactionService {

    private final UserTransactionRepository userTransactionRepository;
    private final SellerSystemClient sellerSystemClient;
    private final PaginationProps paginationProps;

    @Transactional
    public UserTransaction create(UserTransaction userTransaction) {
        sellerSystemClient.getUserById(userTransaction.getCreatedById());
        sellerSystemClient.updatePositionAmount(
                userTransaction.getPositionId(),
                new UpdatePositionAmountRequest(userTransaction.getAmount())
        );
        userTransaction.setCreated(ZonedDateTime.now());
        return userTransactionRepository.save(userTransaction);
    }

    public Page<UserTransaction> getAll(UUID userId, Integer page, Integer size) {
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
}
