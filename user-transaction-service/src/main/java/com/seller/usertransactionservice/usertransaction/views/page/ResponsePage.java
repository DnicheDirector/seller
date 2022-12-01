package com.seller.usertransactionservice.usertransaction.views.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class ResponsePage<T> {
    private List<T> content;
    private Long totalElements;
    private int totalPages;
    private int number;

    public ResponsePage(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.number = page.getNumber();
    }
}
