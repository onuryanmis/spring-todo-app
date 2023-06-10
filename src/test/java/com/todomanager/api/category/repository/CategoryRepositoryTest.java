package com.todomanager.api.category.repository;

import com.todomanager.api.category.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    public void setUp() {
        category = Category.builder()
                .name("Category 1")
                .build();
    }

    @Test
    @DisplayName("[CategoryRepository] findAll() method test")
    public void testFindAll() {
        categoryRepository.save(category);

        List<Category> categoryList = categoryRepository.findAll();

        assertNotNull(categoryList);
        assertEquals(1, categoryList.size());
    }

    @Test
    @DisplayName("[CategoryRepository] findById() method test")
    public void testFindById() {
        Category savedCategory = categoryRepository.save(category);

        Category foundCategory = categoryRepository.findById(savedCategory.getId()).orElse(null);

        assertNotNull(foundCategory);
        assertEquals(savedCategory.getId(), foundCategory.getId());
    }

    @Test
    @DisplayName("[CategoryRepository] save() method test")
    public void testSave() {
        Category savedCategory = categoryRepository.save(category);

        assertNotNull(savedCategory);
        assertEquals(category.getName(), savedCategory.getName());
    }

    @Test
    @DisplayName("[CategoryRepository] deleteById() method test")
    public void testDeleteById() {
        Category savedCategory = categoryRepository.save(category);

        categoryRepository.deleteById(savedCategory.getId());

        Category foundCategory = categoryRepository.findById(savedCategory.getId()).orElse(null);

        assertNull(foundCategory);
    }
}