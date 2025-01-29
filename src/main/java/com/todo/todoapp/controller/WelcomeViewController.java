package com.todo.todoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.components.CustomButton;
import com.todo.todoapp.service.LoadViewService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

@Component
public class WelcomeViewController {

	@FXML
	private Label titleLabel;
	@FXML
	private Button newTaskButton;

	@Autowired
	private LoadViewService loadViewService;

	@FXML
	private void initialize() {
		titleLabel.setText("Application title");
		CustomButton.customize(newTaskButton, "New task");
	}

	@FXML
	public void newTaskAction() {
		System.out.println("Showing new task form...");

		loadViewService.loadFXML(MainWindowController.contentPaneCopy, "new-task-form-view.fxml");
	}
}
