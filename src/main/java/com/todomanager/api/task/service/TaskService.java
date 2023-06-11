package com.todomanager.api.task.service;

import com.todomanager.api.common.exception.NotFoundException;
import com.todomanager.api.task.dto.TaskDTO;
import com.todomanager.api.task.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();

    Task getById(Long id) throws NotFoundException;

    Task create(TaskDTO taskDTO);

    Task update(Long id, TaskDTO taskDTO) throws NotFoundException;

    void delete(Long id) throws NotFoundException;
}
