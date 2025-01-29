package com.todo.todoapp.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.todo.todoapp.domain.model.Task;
import com.todo.todoapp.infrastructure.storage.TaskRepository;

@Service
public class TasksServiceImpl implements TasksService {

	@Autowired
	@Qualifier("TaskRepository")
	private TaskRepository taskRepository;

	@Override
	public void store(Task task) {
		
		List<Task> tasks = taskRepository.getAll();

		task.setId(Long.valueOf(tasks.size() + 1));
		tasks.add(task);

		taskRepository.store(tasks);
	}
}
