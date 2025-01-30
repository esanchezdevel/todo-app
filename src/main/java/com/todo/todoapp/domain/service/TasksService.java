package com.todo.todoapp.domain.service;

import com.todo.todoapp.application.exception.AppException;
import com.todo.todoapp.domain.model.Task;

public interface TasksService {

	/**
	 * Store one task in application storage system
	 * 
	 * @param task The task to be saved
	 * @throws AppException Throws exception when some error happens
	 */
	void store(Task task) throws AppException;
}
