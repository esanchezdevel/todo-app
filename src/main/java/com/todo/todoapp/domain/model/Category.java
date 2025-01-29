package com.todo.todoapp.domain.model;

public class Category extends AppEntity {
	
	private String name;

	public Category() {
		
	}

	public Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Category [name=" + name + "]";
	}
}
