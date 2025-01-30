package com.todo.todoapp.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.application.Constants;
import com.todo.todoapp.application.components.Alerts;
import com.todo.todoapp.application.components.CustomButton;
import com.todo.todoapp.application.exception.AppException;
import com.todo.todoapp.domain.model.Task;
import com.todo.todoapp.domain.model.TasksStatus;
import com.todo.todoapp.domain.service.CategoriesService;
import com.todo.todoapp.domain.service.LoadViewService;
import com.todo.todoapp.domain.service.TasksService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Component
public class NewTaskFormViewController {
	
	private static final Logger logger = LogManager.getLogger(NewTaskFormViewController.class);

	@FXML
	private Label titleLabel, newTaskTitleLabel, nameLabel, categoryLabel;
	@FXML
	private TextField nameField;
	@FXML
	private ComboBox<String> categoryComboBox;
	@FXML
	private CustomButton submitButton;

	@Autowired
	private LoadViewService loadViewService;

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private TasksService tasksService;

	@FXML
	private void initialize() {
		titleLabel.setText(Constants.APP_TITLE + " - " + Constants.APP_VERSION);
		newTaskTitleLabel.setText("New Task");
		nameLabel.setText("Task description: ");
		categoryLabel.setText("Category: ");

		submitButton.setText("Create");
		
		nameField.setPrefWidth(500.0);

		List<String> categories = categoriesService.getAllNames();
		ObservableList<String> observableCategories = FXCollections.observableArrayList(categories);
		categoryComboBox.setItems(observableCategories);
	}

	@FXML
	public void createTask() {
		logger.info("Task to store: " + nameField.getText());
		logger.info("Category: " + categoryComboBox.getValue());

		Task task = new Task();
		task.setTitle(nameField.getText());
		task.setNotes("");
		task.setCategory(categoryComboBox.getValue());
		task.setStatus(TasksStatus.TODO);
		task.setCreated(LocalDateTime.now().toString());
		task.setLastUpdated(null);
		task.setStart(null);
		task.setFinish(null);

		try {
			tasksService.store(task);
		} catch (AppException e) {
			logger.error("Error storing task '" + task + "' in storage system. errorCode: " + e.getCode() + ", errorMessage: " + e.getMessage());
			Alerts.showErrorAlert(e.getMessage());
		}
		
		loadViewService.loadFXML(MainWindowController.contentPaneCopy, "welcome-view.fxml");
	}
}
