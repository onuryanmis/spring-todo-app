package com.todomanager.api.category.controller;

import com.todomanager.api.category.dto.CategoryDTO;
import com.todomanager.api.category.entity.Category;
import com.todomanager.api.category.service.CategoryService;
import com.todomanager.api.common.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<List<Category>> index() {
        return new ApiResponse<>(HttpStatus.OK, "", categoryService.getAllCategories());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Category> store(@Validated @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.create(categoryDTO);

        return new ApiResponse<>(
                HttpStatus.CREATED,
                "Category created successfully.",
                category
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<Category> show(@PathVariable("id") Long id) {
        Category category = categoryService.getById(id);

        return new ApiResponse<>(HttpStatus.OK, "", category);
    }

    @PutMapping("/{id}")
    public ApiResponse<Category> update(
            @PathVariable("id") Long id,
            @Validated @RequestBody CategoryDTO categoryDTO
    ) {
        Category category = categoryService.update(id, categoryDTO);

        return new ApiResponse<>(
                HttpStatus.OK,
                "Category updated successfully.",
                category
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> destroy(@PathVariable Long id) {
        categoryService.delete(id);

        return new ApiResponse<>(
                HttpStatus.OK,
                "Category deleted successfully."
        );
    }
}
