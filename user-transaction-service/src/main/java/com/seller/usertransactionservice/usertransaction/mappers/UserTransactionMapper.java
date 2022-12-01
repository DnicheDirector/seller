package com.seller.usertransactionservice.usertransaction.mappers;

import com.seller.usertransactionservice.usertransaction.models.UserTransaction;
import com.seller.usertransactionservice.usertransaction.views.user.UserTransactionRequest;
import com.seller.usertransactionservice.usertransaction.views.user.UserTransactionResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserTransactionMapper {
    UserTransaction fromDto(UserTransactionRequest dto);

    UserTransactionResponse toDto(UserTransaction userTransaction);
    List<UserTransactionResponse> toDto(List<UserTransaction> userTransaction);
}
