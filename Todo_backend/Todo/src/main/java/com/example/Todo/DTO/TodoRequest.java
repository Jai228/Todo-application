package com.example.Todo.DTO;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class TodoRequest {
    private String title;
    private String description;
    private List<String> tags;
    private LocalDate dueDate;
    private boolean completed;
}