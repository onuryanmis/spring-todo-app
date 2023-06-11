package com.todomanager.api.task.service.impl;

import com.todomanager.api.category.entity.Category;
import com.todomanager.api.category.repository.CategoryRepository;
import com.todomanager.api.common.exception.NotFoundException;
import com.todomanager.api.task.dto.TaskDTO;
import com.todomanager.api.task.entity.Task;
import com.todomanager.api.task.exception.TaskCategoryNotFoundException;
import com.todomanager.api.task.repository.TaskRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    private Category category;

    @BeforeEach
    public void setUp() {
        task = Task.builder()
                .id(1L)
                .title("Java")
                .completed(true)
                .build();

        category = Category.builder()
                .id(1L)
                .name("Java")
                .build();
    }

    @Test
    @DisplayName("[TaskService] - getAllTasks() method")
    public void testGetAllTasks() {
        given(taskRepository.findAll())
                .willReturn(List.of(task));

        List<Task> tasks = taskService.getAllTasks();

        assertThat(tasks).isNotNull();
        assertThat(tasks.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("[TaskService] - getById() method")
    public void testGetById() {
        given(taskRepository.findById(task.getId()))
                .willReturn(Optional.of(task));

        Task task = taskService.getById(1L);

        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(1);
        assertThat(task.getTitle()).isEqualTo("Java");
        assertThat(task.getCompleted()).isTrue();
    }

    @Test
    @DisplayName("[TaskService] - getById() method - Task not found")
    public void testGetByIdWhenTaskNotFound() {
        given(taskRepository.findById(task.getId()))
                .willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.getById(1L));
    }

    @Test
    @DisplayName("[TaskService] - create() method")
    public void testCreate() {
        given(categoryRepository.findById(any(Long.class)))
                .willReturn(Optional.of(category));

        given(taskRepository.save(any(Task.class)))
                .willReturn(task);

        TaskDTO taskDTO = TaskDTO.builder()
                .title("Java")
                .completed(true)
                .build();

        Task task1 = taskService.create(taskDTO);

        assertThat(task1).isNotNull();
        assertThat(task1.getTitle()).isEqualTo("Java");
        assertThat(task1.getCompleted()).isTrue();
    }

    @Test
    @DisplayName("[TaskService] - create() method - Category not found")
    public void testCreateWhenCategoryNotFound() {
        given(categoryRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        TaskDTO taskDTO = TaskDTO.builder()
                .title("Java")
                .completed(true)
                .build();

        assertThrows(TaskCategoryNotFoundException.class, () -> taskService.create(taskDTO));
    }

    @Test
    @DisplayName("[TaskService] - update() method")
    public void testUpdate() {
        given(categoryRepository.findById(any(Long.class)))
                .willReturn(Optional.of(category));

        given(taskRepository.findById(task.getId()))
                .willReturn(Optional.of(task));

        given(taskRepository.save(any(Task.class)))
                .willReturn(task);

        TaskDTO taskDTO = TaskDTO.builder()
                .title("PHP")
                .completed(true)
                .build();

        Task task1 = taskService.update(1L, taskDTO);

        assertThat(task1).isNotNull();
        assertThat(task1.getId()).isEqualTo(1L);
        assertThat(task1.getTitle()).isEqualTo("PHP");
    }

    @Test
    @DisplayName("[TaskService] - update() method - Task not found")
    public void testUpdateWhenTaskNotFound() {
        given(taskRepository.findById(task.getId()))
                .willReturn(Optional.empty());

        TaskDTO taskDTO = TaskDTO.builder()
                .title("PHP")
                .completed(true)
                .build();

        assertThrows(NotFoundException.class, () -> taskService.update(1L, taskDTO));
    }

    @Test
    @DisplayName("[TaskService] - update() method - Category not found")
    public void testUpdateWhenCategoryNotFound() {
        given(categoryRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        given(taskRepository.findById(task.getId()))
                .willReturn(Optional.of(task));

        TaskDTO taskDTO = TaskDTO.builder()
                .title("PHP")
                .completed(true)
                .build();

        assertThrows(TaskCategoryNotFoundException.class, () -> taskService.update(1L, taskDTO));
    }

    @Test
    @DisplayName("[TaskService] - delete() method")
    public void testDelete() {
        given(taskRepository.findById(task.getId()))
                .willReturn(Optional.of(task));

        taskService.delete(1L);

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    @DisplayName("[TaskService] - delete() method - Task not found")
    public void testDeleteWhenTaskNotFound() {
        given(taskRepository.findById(task.getId()))
                .willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> taskService.delete(1L));
    }
}