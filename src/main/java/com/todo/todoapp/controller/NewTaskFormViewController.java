package com.todo.todoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.application.Constants;
import com.todo.todoapp.application.components.CustomButton;
import com.todo.todoapp.domain.service.LoadViewService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Component
public class NewTaskFormViewController {
	
	@FXML
	private Label titleLabel, newTaskTitleLabel, nameLabel;
	@FXML
	private TextField nameField;
	@FXML
	private CustomButton submitButton;

	@Autowired
	private LoadViewService loadViewService;

	@FXML
	private void initialize() {
		titleLabel.setText(Constants.APP_TITLE + " - " + Constants.APP_VERSION);
		newTaskTitleLabel.setText("New Task");
		nameLabel.setText("Task description: ");

		submitButton.setText("Create");
		
		nameField.setPrefWidth(500.0);
	}

	@FXML
	public void createTask() {
		System.out.println("Task to store: " + nameField.getText());

		nameField.setText("");
	}
}
