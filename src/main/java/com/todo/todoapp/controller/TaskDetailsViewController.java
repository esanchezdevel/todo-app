package com.todo.todoapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.domain.model.Task;
import com.todo.todoapp.domain.service.LoadViewService;

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
	private ComboBox categoryComboBox, statusComboBox;

	@Autowired
	private LoadViewService loadViewService;

	@FXML
	private void initialize () {
		logger.info("Initialize view components...");

		titleLabel.setText("Task Details");
		nameLabel.setText("Title: ");
		nameField.setText(String.valueOf(task.getTitle()));
		nameField.setMinWidth(550);
		nameField.setMaxWidth(550);
		categoryLabel.setText("Category:");
		statusLabel.setText("Status:");
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
