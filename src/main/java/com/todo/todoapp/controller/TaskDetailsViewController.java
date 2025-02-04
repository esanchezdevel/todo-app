package com.todo.todoapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@Component
public class TaskDetailsViewController {
	
	private static final Logger logger = LogManager.getLogger(TaskDetailsViewController.class);

	public static Long taskId;

	@FXML
	private TextField nameField;

	@FXML
	private void initialize () {
		logger.info("Initialize view components...");
		nameField.setText(String.valueOf(taskId));
	}

	@FXML
	private void editTask (){
		logger.info("Edit task clicked...");
	}

	@FXML
	private void goBack () {
		logger.info("Go back to welcome view...");
	}
}
