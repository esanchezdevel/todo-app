package com.todo.todoapp.domain.model;

public enum TasksStatus {

	TODO ("ToDo"),
	IN_PROGRESS ("In Progress"),
	DONE ("Done"),
	CANCELLED ("Cancelled");

	private String value;

	private TasksStatus(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
