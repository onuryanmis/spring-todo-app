package com.todomanager.api.task.mapper;

import com.todomanager.api.task.dto.TaskDTO;
import com.todomanager.api.task.entity.Task;

public class TaskMapper {

    public static TaskDTO toDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle(task.getTitle());
        taskDTO.setCompleted(task.getCompleted());

        return taskDTO;
    }

    public static Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setCompleted(taskDTO.getCompleted());

        return task;
    }
}
