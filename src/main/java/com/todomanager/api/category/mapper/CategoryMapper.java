package com.todomanager.api.category.mapper;

import com.todomanager.api.category.dto.CategoryDTO;
import com.todomanager.api.category.entity.Category;

public class CategoryMapper {

    public static CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .build();
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        return Category.builder()
                .name(categoryDTO.getName())
                .build();
    }
}
