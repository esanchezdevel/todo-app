package com.todo.todoapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.application.Constants;
import com.todo.todoapp.application.components.CustomButton;
import com.todo.todoapp.domain.service.LoadViewService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class WelcomeViewController {

	private final static Logger logger = LogManager.getLogger(WelcomeViewController.class);

	@FXML
	private Label titleLabel;
	@FXML
	private CustomButton newTaskButton, newCategoryButton;

	@Autowired
	private LoadViewService loadViewService;

	@FXML
	private void initialize() {
		titleLabel.setText(Constants.APP_TITLE + " - " + Constants.APP_VERSION);
		newTaskButton.setText("New task");
		newCategoryButton.setText("New Category");
	}

	@FXML
	public void newTaskAction() {
		logger.info("Showing new task form...");

		loadViewService.loadFXML(MainWindowController.contentPaneCopy, "new-task-form-view.fxml");
	}

	@FXML
	public void newCategoryAction() {
		logger.info("Showing new category form...");

		loadViewService.loadFXML(MainWindowController.contentPaneCopy, "new-category-form-view.fxml");
	}
}
