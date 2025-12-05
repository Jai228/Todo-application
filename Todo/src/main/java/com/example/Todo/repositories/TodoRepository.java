package com.example.Todo.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Todo.entities.Todo;
import com.example.Todo.entities.User;

public interface TodoRepository extends JpaRepository<Todo, Long> {
   
    // Get all todos of a user
    List<Todo> findByUser(User user);

    // Filter by title containing text
    List<Todo> findByUserAndTitleContainingIgnoreCase(User user, String title);

    // Filter by tags
    List<Todo> findByUserAndTagsIn(User user, List<String> tags);

    // Filter by due date
    List<Todo> findByUserAndDueDate(User user, LocalDate dueDate);


     // For title autocomplete (case-insensitive search)
    @Query("SELECT DISTINCT t.title FROM Todo t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<String> findTitlesByQuery(@Param("query") String query);

    // For tags autocomplete
    @Query("SELECT DISTINCT tag FROM Todo t JOIN t.tags tag WHERE LOWER(tag) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<String> findTagsByQuery(@Param("query") String query);
}
