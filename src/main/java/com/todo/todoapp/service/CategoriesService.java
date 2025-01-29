package com.todo.todoapp.service;

public interface CategoriesService {
	
	/**
	 * Store one category in application storage system
	 * 
	 * @param category The category name to be saved
	 */
	void store(String category);
}
