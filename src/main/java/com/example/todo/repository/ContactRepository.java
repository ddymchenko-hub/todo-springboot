package com.example.todo.repository;

import com.example.todo.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Page<Contact> findByNameContainingIgnoreCaseOrPhoneContaining(String name, String phone, Pageable pageable);
}
