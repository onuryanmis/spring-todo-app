package com.todomanager.api.task.mapper;

import com.todomanager.api.task.dto.TaskDTO;
import com.todomanager.api.task.entity.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    @Test
    @DisplayName("[TaskMapper] - toDTO() method")
    public void testToDTO() {
        Task task = Task.builder()
                .title("Java")
                .completed(true)
                .build();

        TaskDTO taskDTO = TaskMapper.toDTO(task);

        assertNotNull(taskDTO);
        assertEquals(task.getTitle(), taskDTO.getTitle());
        assertEquals(task.getCompleted(), taskDTO.getCompleted());
    }

    @Test
    @DisplayName("[TaskMapper] - toEntity() method")
    public void testToEntity() {
        TaskDTO taskDTO = TaskDTO.builder()
                .title("Java")
                .completed(true)
                .build();

        Task task = TaskMapper.toEntity(taskDTO);

        assertNotNull(task);
        assertEquals(taskDTO.getTitle(), task.getTitle());
        assertEquals(taskDTO.getCompleted(), task.getCompleted());
    }
}