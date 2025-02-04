package com.todo.todoapp.application.components;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Alerts {

	public static final String ERROR_TITLE = "Error!!!";
	public static final String ERROR_HEADER = "Something goes wrong!!!";

	public static final String INFO_TITLE = "Information";
	public static final String INFO_HEADER = "Information message!!!";

	public static final String CONFIRMATION_TITLE = "Confirmation";

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

	public static void showConfirmationAlert(String header, String message, Runnable acceptFunction, Runnable cancelFunction) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(CONFIRMATION_TITLE);
		alert.setHeaderText(header);
		alert.setContentText(message);

        ButtonType acceptButton = new ButtonType("Accept");
        ButtonType cancelButton = new ButtonType("Cancel");

		alert.getButtonTypes().setAll(acceptButton, cancelButton);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent() && result.get() == acceptButton) {
			acceptFunction.run();
        } else {
			cancelFunction.run();
        }
	}
}
