package com.todo.todo_app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.todo.todoapp.JavaFxApplication;

@SpringBootTest(classes = JavaFxApplication.class)
class JavaFxApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
