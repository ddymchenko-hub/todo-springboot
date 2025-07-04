package com.example.todo.controller;

import com.example.todo.model.TodoItem;
import com.example.todo.repository.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TodoItem> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public TodoItem create(@RequestBody TodoItem todo) {
        return repository.save(todo);
    }

    @PutMapping("/{id}")
    public TodoItem update(@PathVariable Long id, @RequestBody TodoItem updatedTodo) {
        return repository.findById(id).map(todo -> {
            todo.setTitle(updatedTodo.getTitle());
            todo.setCompleted(updatedTodo.isCompleted());
            return repository.save(todo);
        }).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
