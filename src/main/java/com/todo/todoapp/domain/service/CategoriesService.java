package com.todo.todoapp.domain.service;

import java.util.List;

import com.todo.todoapp.domain.model.Category;
import com.todo.todoapp.domain.model.TasksStatus;

import javafx.scene.layout.VBox;

public interface CategoriesService {
	
	/**
	 * Store one category in application storage system
	 * 
	 * @param category The category name to be saved
	 */
	void store(String category);

	/**
	 * Delete one category from the storage system.
	 * This method will also delete all the tasks that belongs to that category
	 * 
	 * @param category The category to be deleted
	 */
	void delete(String category);

	/**
	 * Get all the categories names stored in the system
	 * 
	 * @return The List of categories names
	 */
	List<String> getAllNames();

	/**
	 * Get all categories
	 * 
	 * @return The list of categories
	 */
	List<Category> getAll();

	/**
	 * Check if one category is already present in the storage system
	 * 
	 * @param category The category to check
	 * @return True if exists. False if not exists
	 */
	boolean alreadyExists(String category);

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

	/**
	 * Show all the categories in a VBox component with a button to delete them
	 * 
	 * @param categoriesVBox The VBox where to show the list of categories
	 */
	void showCategoriesToBeDeleted(VBox categoriesVBox);
}
