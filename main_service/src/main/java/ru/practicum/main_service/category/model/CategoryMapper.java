package ru.practicum.main_service.category.model;

public class CategoryMapper {

    public static Category fromNewToCategory(NewCategoryDto newCategoryDto) {
        return Category.builder()
                .id(null)
                .name(newCategoryDto.getName())
                .build();
    }

    public static CategoryDto fromCategoryToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
