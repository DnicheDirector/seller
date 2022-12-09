package com.seller.usertransactionservice.sellersystem.client;

import com.seller.usertransactionservice.position.views.PositionResponse;
import com.seller.usertransactionservice.position.views.UpdatePositionAmountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient("seller-system")
public interface SellerSystemClient {
    @PatchMapping("api/positions/{id}/amount")
    void updatePositionAmount(@PathVariable Long id, UpdatePositionAmountRequest request);

    @GetMapping("api/positions/{id}")
    PositionResponse getPositionById(@PathVariable Long id);

    @GetMapping("api/users/{id}")
    void getUserById(@PathVariable UUID id);
}
