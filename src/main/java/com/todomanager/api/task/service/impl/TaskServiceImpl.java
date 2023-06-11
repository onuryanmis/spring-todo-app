package com.todomanager.api.task.service.impl;

import com.todomanager.api.category.entity.Category;
import com.todomanager.api.category.repository.CategoryRepository;
import com.todomanager.api.common.exception.NotFoundException;
import com.todomanager.api.task.dto.TaskDTO;
import com.todomanager.api.task.entity.Task;
import com.todomanager.api.task.exception.TaskCategoryNotFoundException;
import com.todomanager.api.task.mapper.TaskMapper;
import com.todomanager.api.task.repository.TaskRepository;
import com.todomanager.api.task.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getById(Long id) {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isEmpty()) {
            throw new NotFoundException();
        }

        return task.get();
    }

    @Override
    public Task create(TaskDTO taskDTO) {
        Task task = TaskMapper.toEntity(taskDTO);
        Category category = getCategoryById(taskDTO.getCategoryId());
        task.setCategory(category);

        return taskRepository.save(task);
    }

    @Override
    public Task update(Long id, TaskDTO taskDTO) {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isEmpty()) {
            throw new NotFoundException();
        }

        Category category = getCategoryById(taskDTO.getCategoryId());

        Task taskUpdated = task.get();
        taskUpdated.setTitle(taskDTO.getTitle());
        taskUpdated.setCompleted(taskDTO.getCompleted());
        taskUpdated.setCategory(category);

        return taskRepository.save(taskUpdated);
    }

    @Override
    public void delete(Long id) {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isEmpty()) {
            throw new NotFoundException();
        }

        taskRepository.delete(task.get());
    }

    private Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new TaskCategoryNotFoundException();
        }

        return category.get();
    }
}
