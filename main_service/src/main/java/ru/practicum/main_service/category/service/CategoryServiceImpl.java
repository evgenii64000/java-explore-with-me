package ru.practicum.main_service.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main_service.category.model.Category;
import ru.practicum.main_service.category.model.CategoryDto;
import ru.practicum.main_service.category.model.CategoryMapper;
import ru.practicum.main_service.category.model.NewCategoryDto;
import ru.practicum.main_service.category.repository.CategoryRepository;
import ru.practicum.main_service.exceptions.NoDataToUpdateException;
import ru.practicum.main_service.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> getCategories(Pageable pageable) {
        List<Category> categories = categoryRepository.findAll(pageable).toList();
        return categories.stream().map(category -> CategoryMapper.fromCategoryToDto(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new NotFoundException("Event with id=" + id + " was not found.");
        } else {
            return CategoryMapper.fromCategoryToDto(category.get());
        }
    }

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        if (newCategoryDto.getName() == null) {
            throw new NoDataToUpdateException("Отсутствуют данные для создания категории");
        }
        Category category = categoryRepository.save(CategoryMapper.fromNewToCategory(newCategoryDto));
        return CategoryMapper.fromCategoryToDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        if (categoryDto.getId() == null || categoryDto.getName() == null) {
            throw new NoDataToUpdateException("Отсутствуют данные для обновления");
        }
        Optional<Category> category = categoryRepository.findById(categoryDto.getId());
        if (category.isEmpty()) {
            throw new NotFoundException("Event with id=" + categoryDto.getId() + " was not found.");
        }
        Category categoryToUpdate = category.get();
        categoryToUpdate.setName(categoryDto.getName());
        categoryRepository.save(categoryToUpdate);
        return CategoryMapper.fromCategoryToDto(categoryToUpdate);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
