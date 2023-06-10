package com.todomanager.api.category.service.impl;

import com.todomanager.api.category.dto.CategoryDTO;
import com.todomanager.api.category.entity.Category;
import com.todomanager.api.category.mapper.CategoryMapper;
import com.todomanager.api.category.repository.CategoryRepository;
import com.todomanager.api.category.service.CategoryService;
import com.todomanager.api.common.exception.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new NotFoundException();
        }

        return category.get();
    }

    @Override
    public Category create(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toEntity(categoryDTO);

        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, CategoryDTO categoryDTO) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new NotFoundException();
        }

        Category categoryUpdated = category.get();
        categoryUpdated.setName(categoryDTO.getName());

        return categoryRepository.save(categoryUpdated);
    }

    @Override
    public void delete(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new NotFoundException();
        }

        categoryRepository.delete(category.get());
    }
}
