package com.geekbrains.ru.springmvcdemo.controller;

import com.geekbrains.ru.springmvcdemo.domain.Category;
import com.geekbrains.ru.springmvcdemo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final Validator validator;

    @GetMapping("/form")
    public String getCategoryForm(@RequestParam(required = false) Long id, Model model,
                                  @ModelAttribute(value = "violations") String violations) {
        Category category = new Category();
        if (id != null) {
            category = categoryService.findById(id);
        }

        model.addAttribute("category", category);
        model.addAttribute("violations", violations);
        model.addAttribute("categories", categoryService.findAll());

        return "category/form";
    }

    @PostMapping
    public RedirectView saveCategory(Category category, RedirectAttributes attributes) {
        Set<ConstraintViolation<Category>> violationResult = validator.validate(category);
        if (!violationResult.isEmpty()) {
            String violations = violationResult.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));

            attributes.addFlashAttribute("violations", violations);

            return new RedirectView("/category/form");
        }

        categoryService.save(category);

        return new RedirectView("/product/list");
    }


}
