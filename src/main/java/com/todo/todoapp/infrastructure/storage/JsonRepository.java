package com.todo.todoapp.infrastructure.storage;

import java.util.List;

import com.todo.todoapp.domain.model.AppEntity;

public interface JsonRepository<T extends AppEntity> {
	
	/**
	 * Retrieve all the elements
	 * 
	 * @return The list of elements
	 */
	List<T> getAll();

	/**
	 * Store a list of elements
	 * 
	 * @param entities The list of elements to be stored
	 */
	void store(List<T> entities);
}
