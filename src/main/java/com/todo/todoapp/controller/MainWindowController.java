package com.todo.todoapp.controller;

import com.todo.todoapp.components.CustomButton;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class MainWindowController {

	private static final String TITLE = "Daily Tasks";
	private static final String NEW_TASK = "New";
	private static final String CREATE = "Create";
	private static final String TEXT_NAME_LABEL = "Task: ";


	@FXML
	private Label titleLabel;

	@FXML
	private Button newTaskButton;

	@FXML
	private Label nameLabel;

	@FXML
	private TextField nameField;

	@FXML
	private Button submitButton;

	@FXML
	public void initialize() {
		titleLabel.setText(TITLE);
		nameLabel.setText(TEXT_NAME_LABEL);

		CustomButton.customize(newTaskButton, NEW_TASK);
		CustomButton.customize(submitButton, CREATE);

		nameField.setPrefWidth(500.0);

		nameLabel.setVisible(false);
		nameField.setVisible(false);
		submitButton.setVisible(false);
	}

	@FXML
	public void newTaskAction() {
		System.out.println("Showing new task form...");

		newTaskButton.setVisible(false);

		nameLabel.setVisible(true);
		nameField.setVisible(true);
		submitButton.setVisible(true);
	}

	@FXML
	public void createTask() {
		System.out.println("Task to store: " + nameField.getText());

		nameField.setText("");
	}
}
