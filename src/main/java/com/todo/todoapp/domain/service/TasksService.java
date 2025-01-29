package com.todo.todoapp.domain.service;

import com.todo.todoapp.domain.model.Task;

public interface TasksService {

	/**
	 * Store one task in application storage system
	 * 
	 * @param task The task to be saved
	 */
	void store(Task task);
}
