package com.todo.todoapp.controller;

import com.todo.todoapp.components.CustomButton;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class MainWindowController {

	private static final String TITLE = "Daily Tasks";
	private static final String NEW_TASK = "New";


	@FXML
	private Label titleLabel;

	@FXML
	private Button newTaskButton;

	@FXML
	public void initialize() {
		titleLabel.setText(TITLE);

		CustomButton.customize(newTaskButton, NEW_TASK);
	}

	@FXML
	public void newTaskAction() {
		System.out.println("Opening new task form...");
	}
}
