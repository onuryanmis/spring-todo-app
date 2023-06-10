package com.todomanager.api.category.service.impl;

import com.todomanager.api.category.dto.CategoryDTO;
import com.todomanager.api.category.entity.Category;
import com.todomanager.api.category.repository.CategoryRepository;
import com.todomanager.api.common.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    public void setUp() {
        category = Category.builder()
                .id(1L)
                .name("Java")
                .build();
    }

    @Test
    @DisplayName("[CategoryService] - getAllCategories() method")
    public void testGetAllCategories() {
        given(categoryRepository.findAll())
                .willReturn(List.of(category));

        List<Category> categories = categoryService.getAllCategories();

        assertThat(categories).isNotNull();
        assertThat(categories.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("[CategoryService] - getById() method")
    public void testGetById() {
        given(categoryRepository.findById(category.getId()))
                .willReturn(Optional.of(category));

        Category category = categoryService.getById(1L);

        assertThat(category).isNotNull();
        assertThat(category.getId()).isEqualTo(1);
        assertThat(category.getName()).isEqualTo("Java");
    }

    @Test
    @DisplayName("[CategoryService] - getById() method - Category not found")
    public void testGetByIdWhenCategoryNotFound() {
        given(categoryRepository.findById(category.getId()))
                .willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.getById(1L));
    }

    @Test
    @DisplayName("[CategoryService] - create() method")
    public void testCreate() {
        given(categoryRepository.save(any(Category.class)))
                .willReturn(category);

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name("Java")
                .build();

        Category category1 = categoryService.create(categoryDTO);

        assertThat(category1).isNotNull();
        assertThat(category1.getName()).isEqualTo("Java");
    }

    @Test
    @DisplayName("[CategoryService] - update() method")
    public void testUpdate() {
        given(categoryRepository.findById(category.getId()))
                .willReturn(Optional.of(category));

        given(categoryRepository.save(any(Category.class)))
                .willReturn(category);

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name("PHP")
                .build();

        Category category1 = categoryService.update(1L, categoryDTO);

        assertThat(category1).isNotNull();
        assertThat(category1.getId()).isEqualTo(1L);
        assertThat(category1.getName()).isEqualTo("PHP");
    }

    @Test
    @DisplayName("[CategoryService] - update() method - Category not found")
    public void testUpdateWhenCategoryNotFound() {
        given(categoryRepository.findById(category.getId()))
                .willReturn(Optional.empty());

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name("PHP")
                .build();

        assertThrows(NotFoundException.class, () -> categoryService.update(1L, categoryDTO));
    }

    @Test
    @DisplayName("[CategoryService] - delete() method")
    public void testDelete() {
        given(categoryRepository.findById(category.getId()))
                .willReturn(Optional.of(category));

        categoryService.delete(1L);

        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    @DisplayName("[CategoryService] - delete() method - Category not found")
    public void testDeleteWhenCategoryNotFound() {
        given(categoryRepository.findById(category.getId()))
                .willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> categoryService.delete(1L));
    }
}