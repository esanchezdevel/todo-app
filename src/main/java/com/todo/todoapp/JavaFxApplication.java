package com.todo.todoapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.todo.todoapp.application.Constants;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
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
		Scene scene = new Scene(loader.load());

		stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/app_icon.png")));

		stage.setTitle(Constants.APP_TITLE + " - " + Constants.APP_VERSION);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();

		        // Adjust the window size based on the current screen
        adjustWindowSize(stage);

        // Add listeners to detect when the window is moved
        ChangeListener<Number> positionListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                adjustWindowSize(stage);
            }
        };

		stage.xProperty().addListener(positionListener);
		stage.yProperty().addListener(positionListener);
	}

    // Method to adjust the window size based on the screen
    private void adjustWindowSize(Stage stage) {
        // Get the screen where the window is currently displayed
        Screen screen = Screen.getScreensForRectangle(
                stage.getX(), 
                stage.getY(), 
                stage.getWidth(), 
                stage.getHeight()
        ).get(0);

        // Get the screen's visible bounds
        Rectangle2D bounds = screen.getVisualBounds();

		double windowWidth = 825.0;
		double windowHeight = 0.0;
		if (bounds.getHeight() >= 1000.0) {
			windowHeight = 850.0;
		} else {
			windowHeight = 800.0;
		}
        
        // Apply the new size to the Stage
        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight);
    }

	@Override
	public void stop() throws Exception {
		context.close();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
