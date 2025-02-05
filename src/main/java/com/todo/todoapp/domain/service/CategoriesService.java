package com.todo.todoapp.domain.service;

import java.util.List;

import com.todo.todoapp.domain.model.TasksStatus;

public interface CategoriesService {
	
	/**
	 * Store one category in application storage system
	 * 
	 * @param category The category name to be saved
	 */
	void store(String category);

	/**
	 * Get all the categories names stored in the system
	 * 
	 * @return The List of names
	 */
	List<String> getAllNames();

	/**
	 * Check if one category has any task with the current status
	 * 
	 * @param category The category to check
	 * @param status The status to check
	 * @param tasksService The injection of the TasksService Service, to avoid circular references between 
	 * 						TasksService and CategoriesService
	 * @return True if has tasks. False if not has tasks
	 */
	boolean categoryHasTasks(String category, TasksStatus status, TasksService tasksService);
}
