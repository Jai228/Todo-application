package com.example.Todo.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.Todo.DTO.TodoRequest;
import com.example.Todo.DTO.TodoResponse;
import com.example.Todo.entities.Todo;
import com.example.Todo.entities.User;
import com.example.Todo.repositories.TodoRepository;
import com.example.Todo.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    // ✅ Create a new Todo
    public TodoResponse createTodo(TodoRequest request, User user) {
        // Make sure the user is managed (saved)
        User managedUser = userRepository.findByEmail(user.getEmail())
                .orElseGet(() -> userRepository.save(user));

        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .tags(request.getTags())
                .dueDate(request.getDueDate())
                .completed(request.isCompleted())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(managedUser)
                .build();

        todoRepository.save(todo);
        return mapToResponse(todo);
    }

    // ✅ Get all Todos for a user
    public List<TodoResponse> getTodos(User user) {
        User managedUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return todoRepository.findByUser(managedUser)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Update existing Todo
    public TodoResponse updateTodo(Long todoId, TodoRequest request, User user) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        // For dummy user, skip ownership check
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setTags(request.getTags());
        todo.setDueDate(request.getDueDate());
        todo.setCompleted(request.isCompleted());
        todo.setUpdatedAt(LocalDateTime.now());

        todoRepository.save(todo);
        return mapToResponse(todo);
    }

    // ✅ Delete existing Todo
    public void deleteTodo(Long todoId, User user) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        todoRepository.delete(todo);
    }

    // ✅ Search Todos
    public List<TodoResponse> searchTodos(User user, String title, List<String> tags, LocalDate dueDate) {
        User managedUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Todo> todos = todoRepository.findByUser(managedUser);

        if (title != null && !title.isEmpty()) {
            todos = todos.stream()
                    .filter(t -> t.getTitle().toLowerCase().contains(title.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (tags != null && !tags.isEmpty()) {
            todos = todos.stream()
                    .filter(t -> t.getTags().stream().anyMatch(tags::contains))
                    .collect(Collectors.toList());
        }

        if (dueDate != null) {
            todos = todos.stream()
                    .filter(t -> dueDate.equals(t.getDueDate()))
                    .collect(Collectors.toList());
        }

        return todos.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // ✅ Map Todo entity → DTO
    private TodoResponse mapToResponse(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .tags(todo.getTags())
                .dueDate(todo.getDueDate())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .completed(todo.isCompleted())
                .build();
    }
}
