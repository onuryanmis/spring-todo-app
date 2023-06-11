package com.todomanager.api.task.repository;

import com.todomanager.api.category.entity.Category;
import com.todomanager.api.category.repository.CategoryRepository;
import com.todomanager.api.task.entity.Task;
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
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Task task;

    @BeforeEach
    public void setUp() {
        Category category = categoryRepository.save(Category.builder()
                .name("Category 1")
                .build());

        task = Task.builder()
                .title("Task 1")
                .completed(true)
                .category(category)
                .build();
    }

    @Test
    @DisplayName("[TaskRepository] findAll() method test")
    public void testFindAll() {
        taskRepository.save(task);

        List<Task> taskList = taskRepository.findAll();

        assertNotNull(taskList);
        assertEquals(1, taskList.size());
    }

    @Test
    @DisplayName("[TaskRepository] findById() method test")
    public void testFindById() {
        Task savedTask = taskRepository.save(task);

        Task foundTask = taskRepository.findById(savedTask.getId()).orElse(null);

        assertNotNull(foundTask);
        assertEquals(savedTask.getId(), foundTask.getId());
    }

    @Test
    @DisplayName("[TaskRepository] save() method test")
    public void testSave() {
        Task savedTask = taskRepository.save(task);

        assertNotNull(savedTask);
        assertEquals(task.getTitle(), savedTask.getTitle());
    }

    @Test
    @DisplayName("[TaskRepository] deleteById() method test")
    public void testDeleteById() {
        Task savedTask = taskRepository.save(task);

        taskRepository.deleteById(savedTask.getId());

        Task foundTask = taskRepository.findById(savedTask.getId()).orElse(null);

        assertNull(foundTask);
    }
}