package com.geekbrains.ru.springmvcdemo.service.impl;

import com.geekbrains.ru.springmvcdemo.domain.Category;
import com.geekbrains.ru.springmvcdemo.domain.dto.CategoryTree;
import com.geekbrains.ru.springmvcdemo.repository.CategoryRepository;
import com.geekbrains.ru.springmvcdemo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public CategoryTree getCategoryTree() {
        Set<Category> categories = categoryRepository.findAllByParentCategoryIsNull();
        List<CategoryTree.TreeEntry> rootEntries = convertToTreeEntries(categories);

        return CategoryTree.builder()
                .rootCategories(rootEntries)
                .build();
    }

    @Override
    public Category findByAlias(String alias) {
        return categoryRepository.findByAlias(alias);
    }

    private List<CategoryTree.TreeEntry> convertToTreeEntries(Set<Category> rootCategories) {
        if (CollectionUtils.isEmpty(rootCategories)) return Collections.emptyList();

        return rootCategories.stream()
                .map(this::convertToTreeEntry)
                .collect(Collectors.toList());
    }

    private CategoryTree.TreeEntry convertToTreeEntry(Category category) {
        return CategoryTree.TreeEntry.builder()
                .category(category)
                .subCategories(convertToTreeEntries(category.getSubCategories()))
                .build();
    }
}
