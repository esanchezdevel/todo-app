package com.todo.todoapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.application.Constants;
import com.todo.todoapp.application.components.Alerts;
import com.todo.todoapp.application.components.CustomButton;
import com.todo.todoapp.domain.service.CategoriesService;
import com.todo.todoapp.domain.service.LoadViewService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Component
public class NewCategoryFormViewController {
	
	private static final Logger logger = LogManager.getLogger(NewCategoryFormViewController.class);

	@FXML
	private Label titleLabel, newCategoryTitleLabel, nameLabel;
	@FXML
	private TextField nameField;
	@FXML
	private CustomButton submitButton;

	@Autowired
	private LoadViewService loadViewService;

	@Autowired
	private CategoriesService categoriesService;

	@FXML
	private void initialize() {
		titleLabel.setText(Constants.APP_TITLE + " - " + Constants.APP_VERSION);
		newCategoryTitleLabel.setText("New Category");
		nameLabel.setText("Category name: ");

		submitButton.setText("Create");
		
		nameField.setPrefWidth(500.0);
	}

	@FXML
	public void createCategory() {
		logger.info("Category to store: {}", nameField.getText());

		if (nameField.getText() == null || nameField.getText().length() == 0) {
			Alerts.showErrorAlert("Empty category Error", "Mandatory name is empty");
			loadViewService.loadFXML(MainWindowController.contentPaneCopy, "new-category-form-view.fxml");
			return;
		}

		if (categoriesService.alreadyExists(nameField.getText())) {
			Alerts.showErrorAlert("Category already exists", new StringBuilder("The category '")
																						.append(nameField.getText())
																						.append("' already exists")
																						.toString());
			loadViewService.loadFXML(MainWindowController.contentPaneCopy, "new-category-form-view.fxml");
			return;
		}
		categoriesService.store(nameField.getText());
		loadViewService.loadFXML(MainWindowController.contentPaneCopy, "welcome-view.fxml");
	}

	@FXML
	private void goBack () {
		logger.info("Go back to welcome view...");
		loadViewService.loadFXML(MainWindowController.contentPaneCopy, "welcome-view.fxml");
	}
}
