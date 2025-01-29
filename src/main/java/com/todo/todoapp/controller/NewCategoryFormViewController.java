package com.todo.todoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.application.Constants;
import com.todo.todoapp.components.CustomButton;
import com.todo.todoapp.service.CategoriesService;
import com.todo.todoapp.service.LoadViewService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Component
public class NewCategoryFormViewController {
	
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
		System.out.println("Category to store: " + nameField.getText());

		categoriesService.store(nameField.getText());

		loadViewService.loadFXML(MainWindowController.contentPaneCopy, "welcome-view.fxml");
	}
}
