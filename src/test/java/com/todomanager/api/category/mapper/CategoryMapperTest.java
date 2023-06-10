package com.todomanager.api.category.mapper;

import com.todomanager.api.category.dto.CategoryDTO;
import com.todomanager.api.category.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    @Test
    @DisplayName("[CategoryMapper] - toDTO() method")
    public void testToDTO() {
        Category category = Category.builder()
                .name("Java")
                .build();

        CategoryDTO categoryDTO = CategoryMapper.toDTO(category);

        assertNotNull(categoryDTO);
        assertEquals(category.getName(), categoryDTO.getName());
    }

    @Test
    @DisplayName("[CategoryMapper] - toEntity() method")
    public void testToEntity() {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name("Java")
                .build();

        Category category = CategoryMapper.toEntity(categoryDTO);

        assertNotNull(category);
        assertEquals(categoryDTO.getName(), category.getName());
    }
}