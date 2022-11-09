package ru.practicum.main_service.category.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.main_service.category.model.CategoryDto;
import ru.practicum.main_service.category.model.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getCategories(Pageable pageable);

    CategoryDto getCategory(Long id);

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(Long id);
}
