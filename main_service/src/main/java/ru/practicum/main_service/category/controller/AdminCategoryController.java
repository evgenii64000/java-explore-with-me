package ru.practicum.main_service.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main_service.category.model.CategoryDto;
import ru.practicum.main_service.category.model.NewCategoryDto;
import ru.practicum.main_service.category.service.CategoryService;

@Controller
@RequestMapping(path = "/admin/categories")
@Validated
public class AdminCategoryController {

    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PatchMapping
    public ResponseEntity<Object> updateCategory(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody NewCategoryDto newCategoryDto) {
        return new ResponseEntity<>(categoryService.createCategory(newCategoryDto), HttpStatus.OK);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long catId) {
        categoryService.deleteCategory(catId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
