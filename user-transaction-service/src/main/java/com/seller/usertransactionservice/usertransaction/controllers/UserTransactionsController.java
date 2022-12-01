package com.seller.usertransactionservice.usertransaction.controllers;

import com.seller.usertransactionservice.usertransaction.mappers.UserTransactionMapper;
import com.seller.usertransactionservice.usertransaction.services.UserTransactionService;
import com.seller.usertransactionservice.usertransaction.views.page.ResponsePage;
import com.seller.usertransactionservice.usertransaction.views.user.UserTransactionRequest;
import com.seller.usertransactionservice.usertransaction.views.user.UserTransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("user-transactions")
@RequiredArgsConstructor
public class UserTransactionsController {

    private final UserTransactionService userTransactionService;
    private final UserTransactionMapper userTransactionMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponsePage<UserTransactionResponse> getAll(
            @RequestParam UUID userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return new ResponsePage<>(
                userTransactionService.getAll(userId, page, size).map(userTransactionMapper::toDto)
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserTransactionResponse create(@RequestBody @Valid UserTransactionRequest request) {
        var userTransaction = userTransactionMapper.fromDto(request);
        return userTransactionMapper.toDto(
                userTransactionService.create(userTransaction)
        );
    }
}
