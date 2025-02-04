package com.todo.todoapp.domain.service;

import java.util.List;

import com.todo.todoapp.application.exception.AppException;
import com.todo.todoapp.domain.model.Task;
import com.todo.todoapp.domain.model.TasksStatus;

import javafx.scene.layout.VBox;

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

	/**
	 * Show tasks of one specific status in the ScrollPane
	 * 
	 * @param tasksVBox The VBox component where the tasks will be added
	 * @param status The status of the tasks to show
	 */
	void showTasksByStatus(VBox tasksVBox, TasksStatus status);

	/**
	 * Show all tasks with a button to delete them
	 * 
	 * @param tasksVBox The VBox component where the tasks will be added
	 */
	void showTasksToBeDeleted(VBox tasksVBox);

	/**
	 * Update the "status" of one specific task
	 * 
	 * @param task The task to be updated
	 * @param status The new status to be set
	 */
	void updateTaskStatus(Task task, TasksStatus status);

	/**
	 * Update one task in the storage system
	 * 
	 * @param task The task to be updated
	 */
	void update(Task task);
}
