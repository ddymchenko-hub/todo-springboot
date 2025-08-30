package com.example.todo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя обязательно")
    @Size(max = 100, message = "Имя не должно превышать 100 символов")
    private String name;

    @Pattern(regexp = "\\+?[0-9\\- ]{7,15}", message = "Неверный формат номера")
    @Column(unique = true)
    private String phone;

    @Email(message = "Некорректный email")
    private String email;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ✅ Конструктор без аргументов
    public Contact() {}

    // ✅ Установим createdAt автоматически
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // ✅ Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
