package com.todo.todoapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("Starting application...");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
		Scene scene = new Scene(loader.load(), 800, 800);

		stage.setTitle("ToDo Application");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
