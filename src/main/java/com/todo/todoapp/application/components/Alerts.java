package com.todo.todoapp.application.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {

	public static final String ERROR_TITLE = "Error!!!";
	public static final String ERROR_HEADER = "Something goes wrong!!!";

	public static void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(ERROR_TITLE);
		alert.setHeaderText(ERROR_HEADER);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
