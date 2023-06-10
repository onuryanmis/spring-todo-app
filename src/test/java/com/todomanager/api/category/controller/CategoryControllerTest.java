package com.todomanager.api.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todomanager.api.category.dto.CategoryDTO;
import com.todomanager.api.category.entity.Category;
import com.todomanager.api.category.service.CategoryService;
import com.todomanager.api.common.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;

import java.util.List;

@WebMvcTest
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    public void setUp() {
        this.category = Category.builder()
                .id(1L)
                .name("Java Spring Boot")
                .build();
    }

    @Test
    @DisplayName("[CategoryController] index method")
    public void testIndex() throws Exception {
        List<Category> categoryList = List.of(category);

        given(categoryService.getAllCategories()).willReturn(categoryList);

        ResultActions response = mockMvc.perform(get("/api/category")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")))
                .andExpect(jsonPath("$.data.size()", is(categoryList.size())))
                .andExpect(jsonPath("$.data[0].id", greaterThan(0)))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.data[0].name", is(category.getName())));
    }

    @Test
    @DisplayName("[CategoryController] store method")
    public void testStore() throws Exception {
        given(categoryService.create(any(CategoryDTO.class))).willReturn(category);

        ResultActions response = mockMvc.perform(post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("CREATED")))
                .andExpect(jsonPath("$.data.id", greaterThan(0)))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.data.name", is(category.getName())));
    }

    @Test
    @DisplayName("[CategoryController] store method with invalid data")
    public void testStoreWithInvalidData() throws Exception {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name("")
                .build();

        ResultActions response = mockMvc.perform(post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.code", is("VALIDATION_FAILURE")))
                .andExpect(jsonPath("$.messages.size()", is(1)))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    @DisplayName("[CategoryController] show method")
    public void testShow() throws Exception {
        given(categoryService.getById(category.getId())).willReturn(category);

        ResultActions response = mockMvc.perform(
                get("/api/category/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")))
                .andExpect(jsonPath("$.data.id", greaterThan(0)))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.data.name", is(category.getName())));
    }

    @Test
    @DisplayName("[CategoryController] show method when category not found")
    public void testShowWhenCategoryNotFound() throws Exception {
        given(categoryService.getById(category.getId()))
                .willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/category/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.code", is("NOT_FOUND")))
                .andExpect(jsonPath("$.messages.size()", is(1)))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    @DisplayName("[CategoryController] update method")
    public void testUpdate() throws Exception {
        given(categoryService.update(eq(category.getId()), any(CategoryDTO.class)))
                .willReturn(category);

        ResultActions response = mockMvc.perform(put("/api/category/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")))
                .andExpect(jsonPath("$.data.id", greaterThan(0)))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.data.name", is(category.getName())));
    }

    @Test
    @DisplayName("[CategoryController] update method when category not found")
    public void testUpdateWhenCategoryNotFound() throws Exception {
        given(categoryService.update(eq(category.getId()), any(CategoryDTO.class)))
                .willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(put("/api/category/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.code", is("NOT_FOUND")))
                .andExpect(jsonPath("$.messages.size()", is(1)))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    @DisplayName("[CategoryController] delete method")
    public void testDelete() throws Exception {
        willDoNothing().given(categoryService).delete(category.getId());

        ResultActions response = mockMvc.perform(delete("/api/category/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.data", nullValue()));
    }

    @Test
    @DisplayName("[CategoryController] delete method when category not found")
    public void testDeleteWhenCategoryNotFound() throws Exception {
        doThrow(new NotFoundException())
                .when(categoryService)
                .delete(any(Long.class));

        ResultActions response = mockMvc.perform(delete("/api/category/{id}", category.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.code", is("NOT_FOUND")))
                .andExpect(jsonPath("$.messages.size()", is(1)))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}