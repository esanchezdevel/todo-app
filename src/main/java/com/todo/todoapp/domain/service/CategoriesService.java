package com.todo.todoapp.domain.service;

import java.util.List;

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
}
