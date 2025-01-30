package com.todo.todoapp.domain.service;

import java.util.List;

import com.todo.todoapp.application.exception.AppException;
import com.todo.todoapp.domain.model.Task;
import com.todo.todoapp.domain.model.TasksStatus;

public interface TasksService {

	/**
	 * Store one task in application storage system
	 * 
	 * @param task The task to be saved
	 * @throws AppException Throws exception when some error happens
	 */
	void store(Task task) throws AppException;

	/**
	 * Get all the tasks stored in the storage system with one specific status
	 * 
	 * @param status The status to look for
	 * @return The list of Tasks found
	 * @throws AppException When some error happens
	 */
	List<Task> getTasksByStatus(TasksStatus status) throws AppException;
}
