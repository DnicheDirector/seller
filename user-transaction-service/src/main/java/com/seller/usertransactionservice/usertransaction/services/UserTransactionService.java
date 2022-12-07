package com.seller.usertransactionservice.usertransaction.services;

import com.seller.usertransactionservice.sellersystem.client.SellerSystemClient;
import com.seller.usertransactionservice.usertransaction.configuration.PaginationProps;
import com.seller.usertransactionservice.usertransaction.exceptions.UserTransactionException;
import com.seller.usertransactionservice.usertransaction.models.UserTransactionStatus;
import com.seller.usertransactionservice.usertransaction.models.UserTransaction;
import com.seller.usertransactionservice.usertransaction.producers.PositionProducer;
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
    private final PaginationProps paginationProps;
    private final SellerSystemClient sellerSystemClient;
    private final PositionProducer positionProducer;

    @Transactional
    public UserTransaction create(UserTransaction userTransaction) {
        try {
            sellerSystemClient.getUserById(userTransaction.getCreatedById());

            var position = sellerSystemClient.getPositionById(userTransaction.getPositionId());
            if (position.getAmount().compareTo(userTransaction.getAmount()) < 0) {
                throw new UserTransactionException(position.getAmount(), userTransaction.getAmount());
            }

            userTransaction.setCreated(ZonedDateTime.now());
            userTransaction.setStatus(UserTransactionStatus.IN_PROGRESS);
            var saved = userTransactionRepository.save(userTransaction);

            positionProducer.sendReducePositionAmountMessage(userTransaction);

            return saved;
        } catch (Exception e) {
            throw new UserTransactionException(e.getMessage());
        }
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

    @Transactional
    public void updateUserTransactionStatus(Long id, UserTransactionStatus status) {
        userTransactionRepository.updateUserTransactionStatus(id, status);
    }
}
