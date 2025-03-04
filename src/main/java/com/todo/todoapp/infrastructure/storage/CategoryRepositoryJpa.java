package com.todo.todoapp.infrastructure.storage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.todoapp.domain.model.Category;

public interface CategoryRepositoryJpa extends JpaRepository<Category, Long> {

	Optional<Category> findByName(String name);
}
