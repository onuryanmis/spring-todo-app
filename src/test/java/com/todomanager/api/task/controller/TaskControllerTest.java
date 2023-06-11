package com.todomanager.api.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todomanager.api.category.entity.Category;
import com.todomanager.api.common.exception.NotFoundException;
import com.todomanager.api.task.dto.TaskDTO;
import com.todomanager.api.task.entity.Task;
import com.todomanager.api.task.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    private Task task;

    private TaskDTO taskDTO;

    @BeforeEach
    public void setUp() {
        Category category = Category.builder()
                .id(1L)
                .name("Java Spring Boot")
                .build();

        this.task = Task.builder()
                .id(1L)
                .title("Learn Java Spring Boot")
                .completed(true)
                .category(category)
                .build();

        this.taskDTO = TaskDTO.builder()
                .title("Learn Java Spring Boot")
                .completed(true)
                .categoryId(1L)
                .build();
    }

    @Test
    @DisplayName("[TaskController] index method")
    public void testIndex() throws Exception {
        List<Task> taskList = List.of(task);

        given(taskService.getAllTasks()).willReturn(taskList);

        ResultActions response = mockMvc.perform(get("/api/task")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.data.size()", is(taskList.size())))
                .andExpect(jsonPath("$.data[0].id", greaterThan(0)))
                .andExpect(jsonPath("$.data[0].title", is(task.getTitle())))
                .andExpect(jsonPath("$.data[0].completed", is(task.getCompleted())))
                .andExpect(jsonPath("$.data[0].category.id", greaterThan(0)))
                .andExpect(jsonPath("$.data[0].category.name", is(task.getCategory().getName())));
    }

    @Test
    @DisplayName("[TaskController] store method")
    public void testStore() throws Exception {
        given(taskService.create(taskDTO)).willReturn(task);

        ResultActions response = mockMvc.perform(post("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDTO)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("CREATED")))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.data.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.title", is(task.getTitle())))
                .andExpect(jsonPath("$.data.completed", is(task.getCompleted())))
                .andExpect(jsonPath("$.data.category.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.category.name", is(task.getCategory().getName())));
    }

    @Test
    @DisplayName("[TaskController] store method with invalid data")
    public void testStoreWithInvalidData() throws Exception {
        TaskDTO taskDTO = TaskDTO.builder()
                .title("")
                .build();

        ResultActions response = mockMvc.perform(post("/api/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDTO)));

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("BAD_REQUEST")))
                .andExpect(jsonPath("$.code", is("VALIDATION_FAILURE")))
                .andExpect(jsonPath("$.messages.size()", is(2)))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    @DisplayName("[TaskController] show method")
    public void testShow() throws Exception {
        given(taskService.getById(task.getId())).willReturn(task);

        ResultActions response = mockMvc.perform(
                get("/api/task/{id}", task.getId())
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.data.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.title", is(task.getTitle())))
                .andExpect(jsonPath("$.data.completed", is(task.getCompleted())))
                .andExpect(jsonPath("$.data.category.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.category.name", is(task.getCategory().getName())));
    }

    @Test
    @DisplayName("[TaskController] show method when task not found")
    public void testShowWhenTaskNotFound() throws Exception {
        given(taskService.getById(task.getId()))
                .willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(
                get("/api/task/{id}", task.getId())
                        .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.code", is("NOT_FOUND")))
                .andExpect(jsonPath("$.messages.size()", is(1)))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    @DisplayName("[TaskController] update method")
    public void testUpdate() throws Exception {
        given(taskService.update(eq(task.getId()), any(TaskDTO.class)))
                .willReturn(task);

        ResultActions response = mockMvc.perform(put("/api/task/{id}", task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDTO)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.data.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.title", is(task.getTitle())))
                .andExpect(jsonPath("$.data.completed", is(task.getCompleted())))
                .andExpect(jsonPath("$.data.category.id", greaterThan(0)))
                .andExpect(jsonPath("$.data.category.name", is(task.getCategory().getName())));
    }

    @Test
    @DisplayName("[TaskController] update method when task not found")
    public void testUpdateWhenTaskNotFound() throws Exception {
        given(taskService.update(eq(task.getId()), any(TaskDTO.class)))
                .willThrow(new NotFoundException());

        ResultActions response = mockMvc.perform(put("/api/task/{id}", task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskDTO)));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.code", is("NOT_FOUND")))
                .andExpect(jsonPath("$.messages.size()", is(1)))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    @DisplayName("[TaskController] delete method")
    public void testDelete() throws Exception {
        willDoNothing().given(taskService).delete(task.getId());

        ResultActions response = mockMvc.perform(delete("/api/task/{id}", task.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("OK")))
                .andExpect(jsonPath("$.message", notNullValue()))
                .andExpect(jsonPath("$.data", nullValue()));
    }

    @Test
    @DisplayName("[TaskController] delete method when task not found")
    public void testDeleteWhenTaskNotFound() throws Exception {
        doThrow(new NotFoundException())
                .when(taskService)
                .delete(any(Long.class));

        ResultActions response = mockMvc.perform(delete("/api/task/{id}", task.getId())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.code", is("NOT_FOUND")))
                .andExpect(jsonPath("$.messages.size()", is(1)))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}