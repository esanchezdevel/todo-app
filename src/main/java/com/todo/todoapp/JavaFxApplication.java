package com.todo.todoapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.todo.todoapp.application.Constants;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@SpringBootApplication
public class JavaFxApplication extends Application {

	private static ConfigurableApplicationContext context;

	@Override
	public void init() throws Exception {
		context = new SpringApplicationBuilder(JavaFxApplication.class).run();

		SpringContext.setApplicationContext(context);
	}

	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("Starting application...");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
		loader.setControllerFactory(SpringContext::getBean); // Use Spring for controllers
		Scene scene = new Scene(loader.load(), Constants.APP_WIDTH, Constants.APP_HEIGHT);

		stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/app_icon.png")));

		stage.setTitle(Constants.APP_TITLE + " - " + Constants.APP_VERSION);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void stop() throws Exception {
		context.close();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
