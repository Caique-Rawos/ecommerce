package com.example.ecommerce.shared;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {

    private long totalElements;
    private int totalPages;
    private int size;
    private List<T> content;

    public PaginatedResponse(long totalElements, int totalPages, int size, List<T> content) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.size = size;
        this.content = content;
    }

}

