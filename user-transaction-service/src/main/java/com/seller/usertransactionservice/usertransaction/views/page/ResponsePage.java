package com.seller.usertransactionservice.usertransaction.views.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePage<T> {
    private List<T> content;
    private Long totalElements;
    private int totalPages;
    private int currentPage;

    public ResponsePage(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber();
    }
}
