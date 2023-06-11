package com.todomanager.api.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    @NotBlank(message = "Başlık boş olamaz!")
    @Size(max = 255)
    private String title;

    private Boolean completed = false;

    @Positive(message = "Kategori boş olamaz!")
    private long categoryId;
}
