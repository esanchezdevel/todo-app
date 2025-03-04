package com.todo.todoapp.infrastructure.storage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.todoapp.domain.model.Task;
import com.todo.todoapp.domain.model.TasksStatus;

public interface TaskRepositoryJpa extends JpaRepository<Task, Long> {

	List<Task> findByStatusOrderByTitleAsc(TasksStatus status);
}
