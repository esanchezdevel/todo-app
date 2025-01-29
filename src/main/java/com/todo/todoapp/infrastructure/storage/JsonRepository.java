package com.todo.todoapp.infrastructure.storage;

import java.util.List;

import com.todo.todoapp.domain.model.AppEntity;

public interface JsonRepository<T extends AppEntity> {
	
	List<T> getAll();

	void store(List<T> entities);
}
