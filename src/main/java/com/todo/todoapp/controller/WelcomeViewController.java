package com.todo.todoapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.application.Constants;
import com.todo.todoapp.application.components.CustomButton;
import com.todo.todoapp.domain.model.TasksStatus;
import com.todo.todoapp.domain.service.LoadViewService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

@Component
public class WelcomeViewController {

	private final static Logger logger = LogManager.getLogger(WelcomeViewController.class);

	@FXML
	private Label titleLabel, toDoLabel, inProgressLabel, doneLabel, cancelledLabel;
	@FXML
	private CustomButton newTaskButton, newCategoryButton;

	@Autowired
	private LoadViewService loadViewService;

	private static final String TODO_LABEL_INIT_STYLE = "-fx-font-size: 15px;" +
														"-fx-background-color: #aed6f1;" +
														"-fx-padding: 20px 20px 5px 20px;" +
														"-fx-background-radius: 10px 10px 0px 0px;";

	private static final String INPROGRESS_LABEL_INIT_STYLE = "-fx-font-size: 15px;" +
																"-fx-background-color: #f9e79f;" +
																"-fx-padding: 20px 20px 5px 20px;" +
																"-fx-background-radius: 10px 10px 0px 0px;";

	private static final String DONE_LABEL_INIT_STYLE = "-fx-font-size: 15px;" +
														"-fx-background-color: #abebc6;" +
														"-fx-padding: 20px 20px 5px 20px;" +
														"-fx-background-radius: 10px 10px 0px 0px;";

	private static final String CANCELLED_LABEL_INIT_STYLE = "-fx-font-size: 15px;" +
															"-fx-background-color: #f5b7b1;" +
															"-fx-padding: 20px 20px 5px 20px;" +
															"-fx-background-radius: 10px 10px 0px 0px;";


	private static final String TODO_LABEL_ACTIVE_STYLE = "-fx-font-size: 15px;" +
														"-fx-background-color: #aed6f1;" +
														"-fx-text-fill: white;" +
														"-fx-padding: 20px 20px 5px 20px;" +
														"-fx-effect: dropshadow(gaussian, grey, 2, 0.1, 2, -2);" +
														"-fx-background-radius: 10px 10px 0px 0px;";

	private static final String INPROGRESS_LABEL_ACTIVE_STYLE = "-fx-font-size: 15px;" +
																"-fx-background-color: #f9e79f;" +
																"-fx-text-fill: white;" +
																"-fx-padding: 20px 20px 5px 20px;" +
																"-fx-effect: dropshadow(gaussian, grey, 2, 0.1, 2, -2);" +
																"-fx-background-radius: 10px 10px 0px 0px;";

	private static final String DONE_LABEL_ACTIVE_STYLE = "-fx-font-size: 15px;" +
														"-fx-background-color: #abebc6;" +
														"-fx-text-fill: white;" +
														"-fx-padding: 20px 20px 5px 20px;" +
														"-fx-effect: dropshadow(gaussian, grey, 2, 0.1, 2, -2);" +
														"-fx-background-radius: 10px 10px 0px 0px;";

	private static final String CANCELLED_LABEL_ACTIVE_STYLE = "-fx-font-size: 15px;" +
															"-fx-background-color: #f5b7b1;" +
															"-fx-text-fill: white;" +
															"-fx-padding: 20px 20px 5px 20px;" +
															"-fx-effect: dropshadow(gaussian, grey, 2, 0.1, 2, -2);" +
															"-fx-background-radius: 10px 10px 0px 0px;";

	@FXML
	private void initialize() {
		titleLabel.setText(Constants.APP_TITLE + " - " + Constants.APP_VERSION);
		newTaskButton.setText("New task");
		newCategoryButton.setText("New Category");

		toDoLabel.setText(TasksStatus.TODO.value());
		inProgressLabel.setText(TasksStatus.IN_PROGRESS.value());
		doneLabel.setText(TasksStatus.DONE.value());
		cancelledLabel.setText(TasksStatus.CANCELLED.value());

		toDoLabel.setStyle(TODO_LABEL_INIT_STYLE);
		inProgressLabel.setStyle(INPROGRESS_LABEL_INIT_STYLE);
		doneLabel.setStyle(DONE_LABEL_INIT_STYLE);
		cancelledLabel.setStyle(CANCELLED_LABEL_INIT_STYLE);

		toDoLabel.setOnMouseClicked(event -> {
			showToDo();

			toDoLabel.setStyle(TODO_LABEL_ACTIVE_STYLE);
			inProgressLabel.setStyle(INPROGRESS_LABEL_INIT_STYLE);
			doneLabel.setStyle(DONE_LABEL_INIT_STYLE);
			cancelledLabel.setStyle(CANCELLED_LABEL_INIT_STYLE);
		});

		inProgressLabel.setOnMouseClicked(event -> {
			showInProgress();

			toDoLabel.setStyle(TODO_LABEL_INIT_STYLE);
			inProgressLabel.setStyle(INPROGRESS_LABEL_ACTIVE_STYLE);
			doneLabel.setStyle(DONE_LABEL_INIT_STYLE);
			cancelledLabel.setStyle(CANCELLED_LABEL_INIT_STYLE);
		});

		doneLabel.setOnMouseClicked(event -> {
			showDone();

			toDoLabel.setStyle(TODO_LABEL_INIT_STYLE);
			inProgressLabel.setStyle(INPROGRESS_LABEL_INIT_STYLE);
			doneLabel.setStyle(DONE_LABEL_ACTIVE_STYLE);
			cancelledLabel.setStyle(CANCELLED_LABEL_INIT_STYLE);
		});

		cancelledLabel.setOnMouseClicked(event -> {
			showCancelled();

			toDoLabel.setStyle(TODO_LABEL_INIT_STYLE);
			inProgressLabel.setStyle(INPROGRESS_LABEL_INIT_STYLE);
			doneLabel.setStyle(DONE_LABEL_INIT_STYLE);
			cancelledLabel.setStyle(CANCELLED_LABEL_ACTIVE_STYLE);
		});
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

	public void showToDo() {
		logger.info("Show ToDo tasks...");
	}

	public void showInProgress() {
		logger.info("Show InProgress tasks...");
	}

	public void showDone() {
		logger.info("Show Done tasks...");
	}

	public void showCancelled() {
		logger.info("Show Cancelled tasks...");
	}
}
