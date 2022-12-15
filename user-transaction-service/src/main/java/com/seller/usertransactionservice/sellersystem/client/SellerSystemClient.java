package com.seller.usertransactionservice.sellersystem.client;

import com.seller.reactivecachestarter.reactivecache.annotations.ReactiveMonoCacheable;
import com.seller.usertransactionservice.position.views.PositionResponse;
import com.seller.usertransactionservice.position.views.UpdatePositionAmountRequest;
import com.seller.usertransactionservice.user.views.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@ReactiveFeignClient("seller-system")
public interface SellerSystemClient {
    @PatchMapping("api/positions/{id}/amount")
    Mono<Void> updatePositionAmount(@PathVariable Long id, UpdatePositionAmountRequest request);

    @GetMapping("api/positions/{id}")
    Mono<PositionResponse> getPositionById(@PathVariable Long id);

    @GetMapping("api/users/{id}")
    @ReactiveMonoCacheable(cacheName = "users")
    Mono<UserResponse> getUserById(@PathVariable UUID id);
}
