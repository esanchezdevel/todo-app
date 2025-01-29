package com.todo.todoapp.infrastructure.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.todo.todoapp.domain.model.Task;

@Repository("TaskRepository")
public class TaskRepository implements JsonRepository<Task> {

	private static final Logger logger = LogManager.getLogger(TaskRepository.class);

	private static final String FILE_NAME = "tasks.json";

	@Override
	public List<Task> getAll() {
		
		ObjectMapper objectMapper = new ObjectMapper();

		List<Task> tasks = new ArrayList<>();
		try {
			File file = new File(FILE_NAME);
			if (file.exists()) {
				tasks = objectMapper.readValue(file, TypeFactory.defaultInstance().constructCollectionType(List.class, Task.class));
			}
		} catch (Exception e) {
			logger.error("Error getting task from json file. ", e);
		}

		return tasks;
	}

	@Override
	public void store(List<Task> tasks) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File(FILE_NAME), tasks);
		} catch (Exception e) {
			logger.error("Error writting tasks in json file.", e);
		}
	}
}
