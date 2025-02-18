package com.todo.todoapp.application.components;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class CustomButton extends Button {
	
	private static BackgroundFill blueBackground = new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(5), null);

	public CustomButton() {
		super();
		this.getStyleClass().clear();
		customize();
	}

	private void customize() {
		this.setPrefSize(100.0, 20.0);
		this.setBackground(new Background(blueBackground));

		final DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.GRAY);
        dropShadow.setRadius(10);
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        // Apply the shadow effect to the button
        this.setEffect(dropShadow);

		this.setStyle("-fx-padding: 5px 10px 5px 10px;" + 
						"-fx-font-size: 15px;" +
						"-fx-alignment: center;");

		this.setOnMousePressed(event -> {
			this.setTextFill(Color.WHITE);
			this.setEffect(null);
		});

		this.setOnMouseReleased(event -> {
			this.setTextFill(Color.BLACK);
			this.setEffect(dropShadow);
		});
	}
}
