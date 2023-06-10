package com.todomanager.api.category.service;

import com.todomanager.api.category.dto.CategoryDTO;
import com.todomanager.api.category.entity.Category;
import com.todomanager.api.common.exception.NotFoundException;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getById(Long id) throws NotFoundException;

    Category create(CategoryDTO categoryDTO);

    Category update(Long id, CategoryDTO categoryDTO) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
