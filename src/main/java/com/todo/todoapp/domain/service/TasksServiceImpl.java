package com.todo.todoapp.domain.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.todo.todoapp.application.components.Alerts;
import com.todo.todoapp.application.components.CustomButton;
import com.todo.todoapp.application.components.TasksSeparator;
import com.todo.todoapp.application.exception.AppException;
import com.todo.todoapp.controller.MainWindowController;
import com.todo.todoapp.controller.TaskDetailsViewController;
import com.todo.todoapp.domain.model.Task;
import com.todo.todoapp.domain.model.TasksStatus;
import com.todo.todoapp.infrastructure.storage.TaskRepositoryJpa;

import jakarta.transaction.Transactional;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@Service
public class TasksServiceImpl implements TasksService {

	private static final Logger logger = LogManager.getLogger(TasksServiceImpl.class);

	@Autowired
	private TaskRepositoryJpa taskRepository;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private LoadViewService loadViewService;

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void store(Task task) throws AppException {
		
		List<Task> tasks = taskRepository.findAll();

		if (task == null || !StringUtils.hasLength(task.getTitle()) || task.getCategory() == null)
			throw new AppException(HttpStatus.BAD_REQUEST.value(), "Wrong task to be stored");
		if (taskAlreadyExists(tasks, task))
			throw new AppException(HttpStatus.CONFLICT.value(), "The task ' " + task.getTitle() + "' already exists in category '" + task.getCategory() + "'");

		taskRepository.save(task);
	}


	@Override
	public List<Task> getTasksByStatus(TasksStatus status) throws AppException {
		logger.info("Getting tasks with status {}", status.value());
		try {
			List<Task> tasks = taskRepository.findByStatusOrderByTitleAsc(status);

			logger.info("Found '{}' tasks with status {}", tasks.size(), status.value());

			return tasks;
		} catch (Exception e) {
			logger.error("Error getting tasks with status '" + status.value() + "'");
			throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error getting tasks with status '" + status.value() + "'");
		}
	}

	@Override
	@Transactional
	public void showTasksByStatus(VBox tasksVBox, TasksStatus status) {

		List<String> categories = categoriesService.getAllNames();

		List<Task> tasks = getTasksByStatus(status);

		tasksVBox.getChildren().clear(); // Clear the view from previous tasks showed

		categories.forEach(category -> {

			if (!categoriesService.categoryHasTasks(category, status, this)) {
				logger.info("Skip category '{}'. It doesn't have tasks with status '{}'", category, status.value());
				return;
			}

			Label categoryLabel = new Label();
			categoryLabel.setText(category + ":");
			categoryLabel.setMaxWidth(725);
			categoryLabel.setMinWidth(725);
			categoryLabel.setWrapText(true);
			categoryLabel.setStyle("-fx-font-size: 15px; " +
						   	"-fx-font-weight: bold;" +
							"-fx-padding: 2px 10px 2px 10px;" + 
							"-fx-background-color: lightgrey;" +
							"-fx-effect: dropshadow(gaussian, grey, 2, 0.2, 2, 2);"); // top right bottom left
			tasksVBox.getChildren().add(categoryLabel);
			
			tasks.forEach(t -> {
	
				if (!category.equalsIgnoreCase(t.getCategory().getName())) 
					return; // skip this iteration

				HBox hBox = new HBox();
				hBox.setStyle("-fx-padding: 10px 10px 10px 10px;");
	
				Label label = new Label();
				label.setText(status == TasksStatus.DONE && t.getFinish() != null ? t.getFinish().substring(0, 10) + " - " + t.getTitle() : t.getTitle());
				label.setMaxWidth(545);
				label.setMinWidth(545);
				label.setWrapText(true);
				label.setStyle("-fx-font-size: 15px; " +
								"-fx-padding: 10px 10px 10px 10px;"); // top right bottom left

				label.setOnMouseClicked(event -> {
					logger.info("Show task details...");
					TaskDetailsViewController.task = t;
					loadViewService.loadFXML(MainWindowController.contentPaneCopy, "task-details-view.fxml");
				});
				
				ComboBox<String> statusCombo = new ComboBox<>();
				statusCombo.setStyle("-fx-font-size: 14px; " +
				"-fx-padding: 2px 5px 2px 5px; " + // top right bottom left
				"-fx-background-color: #ADD8E6;");
				statusCombo.setMaxWidth(140);
				statusCombo.setMinWidth(140);
				statusCombo.setItems(FXCollections.observableArrayList(List.of(TasksStatus.TODO.value(), 
																				TasksStatus.IN_PROGRESS.value(), 
																				TasksStatus.DONE.value(), 
																				TasksStatus.CANCELLED.value())));
				statusCombo.setValue(status.value()); // The default value
	
				// When a task status changed, update it automatically
				statusCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
					logger.info("New status of task '{}': {}", t.getTitle(), newValue);
					
					 TasksStatus newStatus = switch (newValue) {
						case "ToDo" -> TasksStatus.TODO;
						case "In Progress" -> TasksStatus.IN_PROGRESS;
						case "Done" -> TasksStatus.DONE;
						case "Cancelled" -> TasksStatus.CANCELLED;
						default -> TasksStatus.TODO;
					};
	
					applicationContext.getBean(TasksService.class).updateTaskStatus(t, newStatus);
	
					applicationContext.getBean(TasksService.class).showTasksByStatus(tasksVBox, status);
				});
	
				// Add the title and the combo in horizontal
				hBox.getChildren().add(label);
				hBox.getChildren().add(statusCombo);
	
				// Add the (title + combo) and the separator in vertical
				tasksVBox.getChildren().add(hBox);
				tasksVBox.getChildren().add(new TasksSeparator());
			});
		});
	}

	@Override
	public void showTasksToBeDeleted(VBox tasksVBox) {
		List<String> categories = categoriesService.getAllNames();

		List<Task> tasks = taskRepository.findAll();

		tasksVBox.getChildren().clear(); // Clear the view from previous tasks showed

		categories.forEach(category -> {

			Label categoryLabel = new Label();
			categoryLabel.setText(category + ":");
			categoryLabel.setMaxWidth(725);
			categoryLabel.setMinWidth(725);
			categoryLabel.setWrapText(true);
			categoryLabel.setStyle("-fx-font-size: 15px; " +
						   	"-fx-font-weight: bold;" +
							"-fx-padding: 2px 10px 2px 10px;" + 
							"-fx-background-color: lightgrey;" +
							"-fx-effect: dropshadow(gaussian, grey, 2, 0.2, 2, 2);"); // top right bottom left
			tasksVBox.getChildren().add(categoryLabel);
			
			tasks.forEach(t -> {
	
				if (!category.equalsIgnoreCase(t.getCategory().getName())) 
					return; // skip this iteration

				HBox hBox = new HBox();
				hBox.setStyle("-fx-padding: 10px 10px 10px 10px;");
	
				Label label = new Label();
				label.setText(t.getTitle());
				label.setMaxWidth(575);
				label.setMinWidth(575);
				label.setWrapText(true);
				label.setStyle("-fx-font-size: 15px; " +
								"-fx-padding: 10px 10px 10px 10px;"); // top right bottom left


				CustomButton deleteButton = new CustomButton();
				deleteButton.setText("Remove");
				deleteButton.setOnMouseClicked(event -> {
					logger.info("Remove task: {}", t.getTitle());

					Alerts.showConfirmationAlert("Delete Task!", 
												"Are you sure that you want to delete this task?\n\n '" + t.getTitle() + "'", 
												() -> {
														logger.info("User accepted. Proceed to delete task {}", t.getTitle());
														delete(t);
														showTasksToBeDeleted(tasksVBox);
												}, 
												() -> {
														logger.info("User canceled the operation. Do nothing");
												});
				});
	
				// Add the title and the combo in horizontal
				hBox.getChildren().add(label);
				hBox.getChildren().add(deleteButton);
	
				// Add the (title + combo) and the separator in vertical
				tasksVBox.getChildren().add(hBox);
				tasksVBox.getChildren().add(new TasksSeparator());
			});
		});
	}

	@Override
	@Transactional
	public void updateTaskStatus(Task task, TasksStatus status) {
		Optional<Task> dbTask = taskRepository.findById(task.getId());
		if (dbTask.isEmpty()) {
			logger.warn("Task with id '{}' not found in database", task.getId());
			return;
		}
		dbTask.get().setStatus(status);
		if (status == TasksStatus.DONE || status == TasksStatus.CANCELLED) {
			dbTask.get().setFinish(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		} else if (status == TasksStatus.IN_PROGRESS) {
			dbTask.get().setStart(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			dbTask.get().setFinish("");
		} else if (status == TasksStatus.TODO) {
			dbTask.get().setStart("");
			dbTask.get().setFinish("");
		}
	}


	@Override
	@Transactional
	public void update(Task task) {
		Optional<Task> dbTask = taskRepository.findById(task.getId());
		if (dbTask.isEmpty()) {
			logger.warn("Task with id '{}' not found in database", task.getId());
			return;
		}
		logger.info("Task to be updated found with id {}", dbTask.get().getId());
		dbTask.get().setTitle(task.getTitle());
		dbTask.get().setCategory(task.getCategory());
		dbTask.get().setNotes(task.getNotes());
		dbTask.get().setStatus(task.getStatus());
		dbTask.get().setCreated(task.getCreated());
		dbTask.get().setStart(task.getStart());
		dbTask.get().setFinish(task.getFinish());
		dbTask.get().setLastUpdated(task.getLastUpdated());
	}


	@Override
	public void delete(Task task) {
		Optional<Task> dbTask = taskRepository.findById(task.getId());
		if (dbTask.isEmpty()) {
			logger.warn("Task with id '{}' not found in database", task.getId());
			return;
		}
		taskRepository.deleteById(task.getId());
		logger.info("Task deleted");
	}


	private boolean taskAlreadyExists(List<Task> tasks, Task task) {
		boolean exists = tasks.stream().anyMatch(t -> task.getTitle().equalsIgnoreCase(t.getTitle()) && 
														task.getCategory().getName().equalsIgnoreCase(t.getCategory().getName()));
		if (exists)
			logger.info("The task '" + task.getTitle() + "' already exists in category '" + task.getCategory() + "'");
		
		return exists;
	}
}
