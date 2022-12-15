package com.seller.usertransactionservice.utils;

import com.seller.usertransactionservice.usertransaction.views.page.ResponsePage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaginationUtils {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public <T> Mono<ResponsePage<T>> toPage(Query query, Pageable pageable, Class<T> targetClass) {
        query.with(pageable);
        return reactiveMongoTemplate.find(query, targetClass)
                .collectList()
                .zipWith(reactiveMongoTemplate.count(query, targetClass))
                .map(result -> new ResponsePage<>(new PageImpl<>(result.getT1(), pageable, result.getT2())));
    }
}
