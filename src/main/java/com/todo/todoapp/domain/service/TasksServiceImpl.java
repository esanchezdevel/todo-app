package com.todo.todoapp.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.todo.todoapp.application.exception.AppException;
import com.todo.todoapp.domain.model.Task;
import com.todo.todoapp.domain.model.TasksStatus;
import com.todo.todoapp.infrastructure.storage.TaskRepository;

@Service
public class TasksServiceImpl implements TasksService {

	private static final Logger logger = LogManager.getLogger(TasksServiceImpl.class);

	@Autowired
	@Qualifier("TaskRepository")
	private TaskRepository taskRepository;

	@Override
	public void store(Task task) throws AppException {
		
		List<Task> tasks = taskRepository.getAll();

		if (task == null || !StringUtils.hasLength(task.getTitle()) || !StringUtils.hasLength(task.getCategory()))
			throw new AppException(HttpStatus.BAD_REQUEST.value(), "Wrong task to be stored");
		if (taskAlreadyExists(tasks, task))
			throw new AppException(HttpStatus.CONFLICT.value(), "The task ' " + task.getTitle() + "' already exists in category '" + task.getCategory() + "'");

		task.setId(Long.valueOf(tasks.size() + 1));
		tasks.add(task);

		taskRepository.store(tasks);
	}


	@Override
	public List<Task> getTasksByStatus(TasksStatus status) throws AppException {
		logger.info("Getting tasks with status {}", status.value());
		try {
			List<Task> allTasks = taskRepository.getAll();

			List<Task> filteredTasks = allTasks.stream().filter(t -> t.getStatus().equals(status)).collect(Collectors.toList());
			logger.info("Tasks found: {}", filteredTasks.size());

			return filteredTasks;
		} catch (Exception e) {
			logger.error("Error getting tasks with status '" + status.value() + "'");
			throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error getting tasks with status '" + status.value() + "'");
		}
	}

	private boolean taskAlreadyExists(List<Task> tasks, Task task) {
		boolean exists = tasks.stream().anyMatch(t -> task.getTitle().equalsIgnoreCase(t.getTitle()) && 
														task.getCategory().equalsIgnoreCase(t.getCategory()));
		if (exists)
			logger.info("The task '" + task.getTitle() + "' already exists in category '" + task.getCategory() + "'");
		
		return exists;
	}
}
