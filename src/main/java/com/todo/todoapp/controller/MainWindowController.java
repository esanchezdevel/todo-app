package com.todo.todoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todo.todoapp.service.LoadViewService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

@Component
public class MainWindowController {

	@FXML
	private Pane contentPane;

	public static Pane contentPaneCopy;

	@Autowired
	private LoadViewService loadViewService;

	@FXML
	public void initialize() {
		contentPaneCopy = contentPane;
		loadViewService.loadFXML(contentPaneCopy, "welcome-view.fxml");		
	}
}
