package com.todo.todoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.components.CustomButton;
import com.todo.todoapp.service.LoadViewService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Component
public class NewTaskFormViewController {
	
	@FXML
	private Label titleLabel;
	@FXML
	private Label newTaskTitleLabel;
	@FXML
	private Label nameLabel;
	@FXML
	private TextField nameField;
	@FXML
	private Button submitButton;

	@Autowired
	private LoadViewService loadViewService;

	@FXML
	private void initialize() {
		titleLabel.setText("Application title");
		newTaskTitleLabel.setText("New Task");
		nameLabel.setText("Task description: ");

		CustomButton.customize(submitButton, "Create");
		
		nameField.setPrefWidth(500.0);
	}

	@FXML
	public void createTask() {
		System.out.println("Task to store: " + nameField.getText());

		nameField.setText("");
	}
}
