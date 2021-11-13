package com.geekbrains.ru.springmvcdemo.domain.dto;

import com.geekbrains.ru.springmvcdemo.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryTree {

    private List<TreeEntry> rootCategories;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TreeEntry {
        private Category category;
        private List<TreeEntry> subCategories;
    }
}