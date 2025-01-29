package com.todo.todoapp.domain.service;

import org.springframework.stereotype.Service;

import com.todo.todoapp.SpringContext;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

@Service
public class LoadViewServiceImpl implements LoadViewService {
	
	public void loadFXML(Pane contentPane, String fxmlFile) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFile));
			loader.setControllerFactory(SpringContext::getBean);
			Parent newView = loader.load();
			contentPane.getChildren().setAll(newView);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
