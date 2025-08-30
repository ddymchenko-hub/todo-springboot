package com.example.todo.controller;

import com.example.todo.model.TodoItem;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public List<TodoItem> getAll() {
        return todoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<TodoItem> createTodo(@RequestBody TodoItem todoItem) {
        TodoItem saved = todoRepository.save(todoItem);
        return ResponseEntity.ok(saved);
    }


    @PutMapping("/{id}")
    public TodoItem update(@PathVariable Long id, @RequestBody TodoItem item) {
        item.setId(id);
        return todoRepository.save(item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}
//public class TodoController {
//
//    private final TodoRepository repository;
//
//    public TodoController(TodoRepository repository) {
//        this.repository = repository;
//    }
//
//    @GetMapping
//    public List<TodoItem> getAll() {
//        return repository.findAll();
//    }
//
//    @PostMapping
//    public TodoItem create(@RequestBody TodoItem todo) {
//        return repository.save(todo);
//    }
//
//    @PutMapping("/{id}")
//    public TodoItem update(@PathVariable Long id, @RequestBody TodoItem updatedTodo) {
//        return repository.findById(id).map(todo -> {
//            todo.setTitle(updatedTodo.getTitle());
//            todo.setCompleted(updatedTodo.isCompleted());
//            return repository.save(todo);
//        }).orElseThrow();
//    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable Long id) {
//        repository.deleteById(id);
//    }
//}
