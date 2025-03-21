package com.todo.todoapp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.application.Constants;
import com.todo.todoapp.application.components.Alerts;
import com.todo.todoapp.domain.model.Category;
import com.todo.todoapp.domain.model.Task;
import com.todo.todoapp.domain.model.TasksStatus;
import com.todo.todoapp.domain.service.CategoriesService;
import com.todo.todoapp.domain.service.LoadViewService;
import com.todo.todoapp.domain.service.TasksService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@Component
public class TaskDetailsViewController {
	
	private static final Logger logger = LogManager.getLogger(TaskDetailsViewController.class);

	public static Task task;

	@FXML
	private Label titleLabel, taskDetailsLabel, nameLabel, categoryLabel, createdLabel, createdValueLabel, startedLabel, 
					startedValueLabel, finishedLabel, finishedValueLabel, lastUpdatedLabel, lastUpdatedValueLabel, 
					statusLabel, notesLabel;
	@FXML
	private TextField nameField;
	@FXML
	private ComboBox<String> categoryComboBox, statusComboBox;
	@FXML
	private TextArea notesTextArea;

	@Autowired
	private LoadViewService loadViewService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private TasksService tasksService;

	@FXML
	private void initialize () {
		logger.info("Initialize view components...");

		titleLabel.setText(Constants.APP_TITLE + " - " + Constants.APP_VERSION);
		taskDetailsLabel.setText("Task Details");
		nameLabel.setText("Title: ");
		nameField.setText(String.valueOf(task.getTitle()));
		nameField.setMinWidth(600);
		nameField.setMaxWidth(600);

		categoryLabel.setText("Category:");
		categoryLabel.setMinWidth(200);
		categoryLabel.setMaxWidth(200);
		
		statusLabel.setText("Status:");
		statusLabel.setMinWidth(200);
		statusLabel.setMaxWidth(200);

		notesLabel.setText("Notes:");
		notesLabel.setMinWidth(200);
		notesLabel.setMaxWidth(200);

		categoryComboBox.setStyle("-fx-font-size: 14px; " +
			"-fx-padding: 2px 5px 2px 5px; " + // top right bottom left
			"-fx-background-color: #ADD8E6;");
		categoryComboBox.setItems(FXCollections.observableArrayList(categoriesService.getAllNames()));
		categoryComboBox.setValue(task.getCategory().getName());

		statusComboBox.setStyle("-fx-font-size: 14px; " +
			"-fx-padding: 2px 5px 2px 5px; " + // top right bottom left
			"-fx-background-color: #ADD8E6;");
		statusComboBox.setItems(FXCollections.observableArrayList(List.of(TasksStatus.TODO.value(), 
																			TasksStatus.IN_PROGRESS.value(), 
																			TasksStatus.DONE.value(), 
																			TasksStatus.CANCELLED.value())));
		statusComboBox.setValue(task.getStatus().value());

		createdLabel.setText("Create:");
		startedLabel.setText("Start:");
		finishedLabel.setText("Finish:");
		lastUpdatedLabel.setText("Last Update:");

		createdValueLabel.setText(task.getCreated());
		startedValueLabel.setText(task.getStart());
		finishedValueLabel.setText(task.getFinish());
		lastUpdatedValueLabel.setText(task.getLastUpdated());

		notesTextArea.setText(task.getNotes());
		notesTextArea.setWrapText(true);
	}

	@FXML
	private void editTask (){
		logger.info("Edit task '{}' clicked...", task.getId());


		Optional<Category> category = categoriesService.getCategory(categoryComboBox.getValue());

		Task editedTask = new Task();
		editedTask.setId(task.getId());
		editedTask.setTitle(nameField.getText());
		editedTask.setCategory(category.get());
		editedTask.setCreated(task.getCreated());
		editedTask.setLastUpdated(task.getLastUpdated());
		editedTask.setStart(task.getStart());
		editedTask.setFinish(task.getFinish());

		TasksStatus newStatus = switch (statusComboBox.getValue()) {
			case "ToDo" -> TasksStatus.TODO;
			case "In Progress" -> TasksStatus.IN_PROGRESS;
			case "Done" -> TasksStatus.DONE;
			case "Cancelled" -> TasksStatus.CANCELLED;
			default -> TasksStatus.TODO;
		};
		editedTask.setStatus(newStatus);
		editedTask.setNotes(notesTextArea.getText());

		if (newStatus == TasksStatus.DONE || newStatus == TasksStatus.CANCELLED) {
			editedTask.setFinish(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		} else if (newStatus == TasksStatus.IN_PROGRESS && task.getStatus() != TasksStatus.IN_PROGRESS) {
			editedTask.setStart(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			editedTask.setFinish("");
		} else if (newStatus == TasksStatus.TODO) {
			editedTask.setStart("");
			editedTask.setFinish("");
		}

		editedTask.setLastUpdated(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

		logger.info("New task data: {}", editedTask);
		tasksService.update(editedTask);

		Alerts.showInfoAlert("Task Updated");
		TaskDetailsViewController.task = editedTask;
		loadViewService.loadFXML(MainWindowController.contentPaneCopy, "task-details-view.fxml");
	}

	@FXML
	private void goBack () {
		logger.info("Go back to welcome view...");
		WelcomeViewController.currentStatus = task.getStatus();
		loadViewService.loadFXML(MainWindowController.contentPaneCopy, "welcome-view.fxml");
	}
}
