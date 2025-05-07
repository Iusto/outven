package com.example.outven.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PageInfo {
    private int currentPage;
    private int totalPages;
    private List<Integer> pageNumbers;

    public PageInfo(int currentPage, int totalPages, List<Integer> pageNumbers) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.pageNumbers = pageNumbers;
    }

    public boolean isFirst() {
        return currentPage == 0;
    }

    public boolean isLast() {
        return currentPage == totalPages - 1;
    }

    public int getPrevPage() {
        return Math.max(0, currentPage - 1);
    }

    public int getNextPage() {
        return Math.min(totalPages - 1, currentPage + 1);
    }
}
