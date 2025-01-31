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

import com.todo.todoapp.application.components.TasksSeparator;
import com.todo.todoapp.application.exception.AppException;
import com.todo.todoapp.domain.model.Task;
import com.todo.todoapp.domain.model.TasksStatus;
import com.todo.todoapp.infrastructure.storage.TaskRepository;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

	@Override
	public void showTasksByStatus(VBox tasksVBox, TasksStatus status) {
		List<Task> tasks = getTasksByStatus(status);

		tasksVBox.getChildren().clear();
		tasks.forEach(t -> {

			HBox hBox = new HBox();
			hBox.setStyle("-fx-padding: 10px 10px 10px 10px;");

			Label label = new Label();
			label.setText(t.getTitle());
			label.setMaxWidth(550);
			label.setMinWidth(550);
			label.setWrapText(true);
			label.setStyle("-fx-font-size: 15px; " +
							"-fx-padding: 10px 10px 10px 10px;"); // top right bottom left
			
			ComboBox<String> statusCombo = new ComboBox<>();
			statusCombo.setStyle("-fx-font-size: 12px; " +
			"-fx-padding: 5px 5px 5px 5px; " + // top right bottom left
			"-fx-background-color: #ADD8E6;");
			statusCombo.setMaxWidth(115);
			statusCombo.setMinWidth(115);
			statusCombo.setItems(FXCollections.observableArrayList(List.of(TasksStatus.TODO.value(), 
																			TasksStatus.IN_PROGRESS.value(), 
																			TasksStatus.DONE.value(), 
																			TasksStatus.CANCELLED.value())));
			statusCombo.setValue(status.value()); // The default value

			// Add the title and the combo in horizontal
			hBox.getChildren().add(label);
			hBox.getChildren().add(statusCombo);

			// Add the (title + combo) and the separator in vertical
			tasksVBox.getChildren().add(hBox);
			tasksVBox.getChildren().add(new TasksSeparator());
		});
	}


	private boolean taskAlreadyExists(List<Task> tasks, Task task) {
		boolean exists = tasks.stream().anyMatch(t -> task.getTitle().equalsIgnoreCase(t.getTitle()) && 
														task.getCategory().equalsIgnoreCase(t.getCategory()));
		if (exists)
			logger.info("The task '" + task.getTitle() + "' already exists in category '" + task.getCategory() + "'");
		
		return exists;
	}
}
