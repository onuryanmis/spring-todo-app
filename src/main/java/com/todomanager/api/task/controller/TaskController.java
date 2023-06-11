package com.todomanager.api.task.controller;

import com.todomanager.api.common.response.ApiResponse;
import com.todomanager.api.task.dto.TaskDTO;
import com.todomanager.api.task.entity.Task;
import com.todomanager.api.task.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ApiResponse<List<Task>> index() {
        return new ApiResponse<>(HttpStatus.OK, "", taskService.getAllTasks());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Task> store(@Validated @RequestBody TaskDTO taskDTO) {
        Task task = taskService.create(taskDTO);

        return new ApiResponse<>(
                HttpStatus.CREATED,
                "Task created successfully.",
                task
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<Task> show(@PathVariable("id") Long id) {
        Task task = taskService.getById(id);

        return new ApiResponse<>(HttpStatus.OK, "", task);
    }

    @PutMapping("/{id}")
    public ApiResponse<Task> update(
            @PathVariable("id") Long id,
            @Validated @RequestBody TaskDTO taskDTO
    ) {
        Task task = taskService.update(id, taskDTO);

        return new ApiResponse<>(
                HttpStatus.OK,
                "Task updated successfully.",
                task
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> destroy(@PathVariable Long id) {
        taskService.delete(id);

        return new ApiResponse<>(
                HttpStatus.OK,
                "Task deleted successfully."
        );
    }
}
