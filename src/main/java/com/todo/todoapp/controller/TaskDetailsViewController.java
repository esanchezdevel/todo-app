package com.todo.todoapp.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.domain.model.Task;
import com.todo.todoapp.domain.model.TasksStatus;
import com.todo.todoapp.domain.service.CategoriesService;
import com.todo.todoapp.domain.service.LoadViewService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Component
public class TaskDetailsViewController {
	
	private static final Logger logger = LogManager.getLogger(TaskDetailsViewController.class);

	public static Task task;

	@FXML
	private Label titleLabel, nameLabel, categoryLabel, statusLabel;
	@FXML
	private TextField nameField;
	@FXML
	private ComboBox<String> categoryComboBox, statusComboBox;

	@Autowired
	private LoadViewService loadViewService;

	@Autowired
	private CategoriesService categoriesService;

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
	}

	@FXML
	private void editTask (){
		logger.info("Edit task clicked...");
	}

	@FXML
	private void goBack () {
		logger.info("Go back to welcome view...");
		loadViewService.loadFXML(MainWindowController.contentPaneCopy, "welcome-view.fxml");
	}
}
