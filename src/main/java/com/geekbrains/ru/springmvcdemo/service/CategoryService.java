package com.geekbrains.ru.springmvcdemo.service;

import com.geekbrains.ru.springmvcdemo.domain.Category;
import com.geekbrains.ru.springmvcdemo.domain.dto.CategoryTree;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category findById(Long id);

    void save(Category category);

    CategoryTree getCategoryTree();

    Category findByAlias(String alias);
}
