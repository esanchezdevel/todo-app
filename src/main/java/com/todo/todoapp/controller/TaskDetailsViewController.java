package com.todo.todoapp.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.application.components.Alerts;
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
	private Label titleLabel, nameLabel, categoryLabel, statusLabel, notesLabel;
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

		titleLabel.setText("Task Details");
		nameLabel.setText("Title: ");
		nameField.setText(String.valueOf(task.getTitle()));
		nameField.setMinWidth(550);
		nameField.setMaxWidth(550);

		categoryLabel.setText("Category:");
		categoryLabel.setMinWidth(200);
		categoryLabel.setMaxWidth(200);
		
		statusLabel.setText("Status:");
		statusLabel.setMinWidth(200);
		statusLabel.setMaxWidth(200);

		notesLabel.setText("Notes:");
		notesLabel.setMinWidth(200);
		notesLabel.setMaxWidth(200);

		categoryComboBox.setStyle("-fx-font-size: 12px; " +
			"-fx-padding: 5px 5px 5px 5px; " + // top right bottom left
			"-fx-background-color: #ADD8E6;");
		categoryComboBox.setMaxWidth(500);
		categoryComboBox.setMinWidth(500);
		categoryComboBox.setItems(FXCollections.observableArrayList(categoriesService.getAllNames()));
		categoryComboBox.setValue(task.getCategory());

		statusComboBox.setStyle("-fx-font-size: 12px; " +
			"-fx-padding: 5px 5px 5px 5px; " + // top right bottom left
			"-fx-background-color: #ADD8E6;");
		statusComboBox.setMaxWidth(120);
		statusComboBox.setMinWidth(120);
		statusComboBox.setItems(FXCollections.observableArrayList(List.of(TasksStatus.TODO.value(), 
																			TasksStatus.IN_PROGRESS.value(), 
																			TasksStatus.DONE.value(), 
																			TasksStatus.CANCELLED.value())));
		statusComboBox.setValue(task.getStatus().value());

		notesTextArea.setText(task.getNotes());
		notesTextArea.setWrapText(true);
	}

	@FXML
	private void editTask (){
		logger.info("Edit task '{}' clicked...", task.getId());

		Task editedTask = new Task();
		editedTask.setId(task.getId());
		editedTask.setTitle(nameField.getText());
		editedTask.setCategory(categoryComboBox.getValue());

		TasksStatus newStatus = switch (statusComboBox.getValue()) {
			case "ToDo" -> TasksStatus.TODO;
			case "In Progress" -> TasksStatus.IN_PROGRESS;
			case "Done" -> TasksStatus.DONE;
			case "Cancelled" -> TasksStatus.CANCELLED;
			default -> TasksStatus.TODO;
		};
		editedTask.setStatus(newStatus);
		editedTask.setNotes(notesTextArea.getText());

		logger.info("New task data: {}", editedTask);
		tasksService.update(editedTask);

		Alerts.showInfoAlert("Task Updated");
	}

	@FXML
	private void goBack () {
		logger.info("Go back to welcome view...");
		loadViewService.loadFXML(MainWindowController.contentPaneCopy, "welcome-view.fxml");
	}
}
