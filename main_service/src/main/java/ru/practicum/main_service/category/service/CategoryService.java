package ru.practicum.main_service.category.service;

import ru.practicum.main_service.category.model.CategoryDto;
import ru.practicum.main_service.category.model.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategory(Long id);

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(Long id);
}
