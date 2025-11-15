package com.example.Todo.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private List<String> tags;
    private LocalDate dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean completed;
    
}