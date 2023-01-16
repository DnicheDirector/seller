package com.seller.usertransactionservice.usertransaction.controllers;

import com.seller.usertransactionservice.usertransaction.mappers.UserTransactionMapper;
import com.seller.usertransactionservice.usertransaction.services.UserTransactionService;
import com.seller.usertransactionservice.usertransaction.views.page.ResponsePage;
import com.seller.usertransactionservice.usertransaction.views.user.UserTransactionRequest;
import com.seller.usertransactionservice.usertransaction.views.user.UserTransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
    public Mono<ResponsePage<UserTransactionResponse>> getAll(
            @RequestParam UUID userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return userTransactionService.getAll(userId, page, size).map(userTransactionMapper::toDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserTransactionResponse> create(@RequestBody @Valid UserTransactionRequest request) {
        var userTransaction = userTransactionMapper.fromDto(request);
        return userTransactionService.create(userTransaction).map(userTransactionMapper::toDto);
    }
}
