package com.todo.todoapp.application.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Alerts {

	public static final String ERROR_TITLE = "Error!!!";
	public static final String ERROR_HEADER = "Something goes wrong!!!";

	public static final String INFO_TITLE = "Information";
	public static final String INFO_HEADER = "Information message!!!";

	public static void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(ERROR_TITLE);
		alert.setHeaderText(ERROR_HEADER);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void showInfoAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(INFO_TITLE);
		alert.setHeaderText(INFO_HEADER);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
