package com.todo.todoapp.components;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class CustomButton {
	
	private static BackgroundFill blueBackground = new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), null);

	public static void customize(Button button, String text) {
		button.setText(text);
		button.setPrefSize(100.0, 20.0);
		button.setBackground(new Background(blueBackground));

		final DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        // Apply the shadow effect to the button
        button.setEffect(dropShadow);

		button.setOnMousePressed(event -> {
			button.setTextFill(Color.WHITE);
			button.setEffect(null);
		});

		button.setOnMouseReleased(event -> {
			button.setTextFill(Color.BLACK);
			button.setEffect(dropShadow);
		});
	}
}
