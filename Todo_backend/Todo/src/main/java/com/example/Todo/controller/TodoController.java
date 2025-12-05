package com.example.Todo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.example.Todo.DTO.TodoRequest;
import com.example.Todo.DTO.TodoResponse;
import com.example.Todo.entities.User;
import com.example.Todo.repositories.TodoRepository;
import com.example.Todo.repositories.UserRepository;
import com.example.Todo.services.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
// @RequiredArgsConstructor
public class TodoController {
@Autowired
    private  TodoService todoService;
    private  TodoRepository todoRepository;
    private  UserRepository userRepository;

    // ✅ Create or get a dummy user automatically
    private User getOrCreateDummyUser() {
        return userRepository.findByEmail("dummy@example.com")
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail("dummy@example.com");
                    newUser.setName("Dummy User");
                    return userRepository.save(newUser);
                });
    }

    // ✅ Create Todo
    @PostMapping
    public TodoResponse createTodo(@RequestBody TodoRequest request) {
        User dummyUser = getOrCreateDummyUser();
        return todoService.createTodo(request, dummyUser);
    }

    // ✅ Get all Todos
    @GetMapping
    public List<TodoResponse> getTodos() {
        User dummyUser = getOrCreateDummyUser();
        return todoService.getTodos(dummyUser);
    }

    // ✅ Update Todo
    @PutMapping("/{id}")
    public TodoResponse updateTodo(@PathVariable Long id, @RequestBody TodoRequest request) {
        User dummyUser = getOrCreateDummyUser();
        return todoService.updateTodo(id, request, dummyUser);
    }

    // ✅ Delete Todo
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        User dummyUser = getOrCreateDummyUser();
        todoService.deleteTodo(id, dummyUser);
    }

    // ✅ Search Todos
    @GetMapping("/search")
    public List<TodoResponse> searchTodos(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dueDate) {
        User dummyUser = getOrCreateDummyUser();
        return todoService.searchTodos(dummyUser, title, tags, dueDate);
    }

    // ✅ Autocomplete Titles
    @GetMapping("/autocomplete/titles")
    public List<String> autocompleteTitles(@RequestParam String query) {
        return todoRepository.findTitlesByQuery(query);
    }

    // ✅ Autocomplete Tags
    @GetMapping("/autocomplete/tags")
    public List<String> autocompleteTags(@RequestParam String query) {
        return todoRepository.findTagsByQuery(query);
    }
}
