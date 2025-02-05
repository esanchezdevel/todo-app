package com.todo.todoapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.application.Constants;
import com.todo.todoapp.application.components.CustomButton;
import com.todo.todoapp.domain.service.LoadViewService;
import com.todo.todoapp.domain.service.TasksService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

@Component
public class DeleteTaskViewController {
	
	private static Logger logger = LogManager.getLogger(DeleteTaskViewController.class);

	@FXML
	private Label titleLabel;
	@FXML
	private CustomButton returnButton;
	@FXML
	private VBox tasksVBox;

	@Autowired
	private LoadViewService loadViewService;

	@Autowired
	private TasksService tasksService;

	@FXML
	private void initialize() {
		titleLabel.setText(Constants.APP_TITLE + " - " + Constants.APP_VERSION);
		returnButton.setText("Return");

		tasksService.showTasksToBeDeleted(tasksVBox);
	}

	@FXML
	private void goBack () {
		logger.info("Go back to welcome view...");
		loadViewService.loadFXML(MainWindowController.contentPaneCopy, "welcome-view.fxml");
	}
}
