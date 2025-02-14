package com.todo.todoapp.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.todo.todoapp.application.components.Alerts;
import com.todo.todoapp.application.components.CustomButton;
import com.todo.todoapp.application.components.TasksSeparator;
import com.todo.todoapp.domain.model.Category;
import com.todo.todoapp.domain.model.Task;
import com.todo.todoapp.domain.model.TasksStatus;
import com.todo.todoapp.infrastructure.storage.JsonRepository;
import com.todo.todoapp.infrastructure.storage.TaskRepository;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@Service
public class CategoriesServiceImpl implements CategoriesService {

	private static final Logger logger = LogManager.getLogger(CategoriesServiceImpl.class);

	@Autowired
	@Qualifier("CategoryRepository")
	private JsonRepository<Category> categoryRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public void store(String category) {
		// get already existing categories
		List<Category> categories = categoryRepository.getAll();

		// add the new category
		categories.add(new Category(category));

		// store all the categories in the file overwritting it
		categoryRepository.store(categories);		
	}


	@Override
	public List<String> getAllNames() {
		List<Category> categories = categoryRepository.getAll();

		return categories
					.stream()
					.map(Category::getName)
					.collect(Collectors.toList());
	}


	@Override
	public List<Category> getAll() {
		return categoryRepository.getAll();
	}


	@Override
	public boolean alreadyExists(String category) {
		List<String> categories = getAllNames();
		return categories.stream().anyMatch(c -> c.equalsIgnoreCase(category));
	}


	@Override
	public boolean categoryHasTasks(String category, TasksStatus status, TasksService tasksService) {
		List<Task> tasks = tasksService.getTasksByStatus(status);
		boolean result = false;
		for (Task task : tasks) {
			if (category.equals(task.getCategory())) {
				result = true;
				break;
			}
		}
		return result;
	}


	@Override
	public void showCategoriesToBeDeleted(VBox categoriesVBox) {
		List<String> categories = getAllNames();

		categoriesVBox.getChildren().clear(); // Clear the view from previous tasks showed
			
		categories.forEach(c -> {
	
			HBox hBox = new HBox();
			hBox.setStyle("-fx-padding: 10px 10px 10px 10px;");

			Label label = new Label();
			label.setText(c);
			label.setMaxWidth(550);
			label.setMinWidth(550);
			label.setWrapText(true);
			label.setStyle("-fx-font-size: 15px; " +
							"-fx-padding: 10px 10px 10px 10px;"); // top right bottom left


			CustomButton deleteButton = new CustomButton();
			deleteButton.setText("Remove");
			deleteButton.setOnMouseClicked(event -> {
				logger.info("Remove category: {}", c);

				Alerts.showConfirmationAlert("Delete Category", 
											"Are you sure that you want to delete this category and all it's tasks?\n\n '" + c + "'", 
											() -> {
													logger.info("User accepted. Proceed to delete task {}", c);
													delete(c);
													showCategoriesToBeDeleted(categoriesVBox);
											}, 
											() -> {
													logger.info("User canceled the operation. Do nothing");
											});
			});

			// Add the title and the combo in horizontal
			hBox.getChildren().add(label);
			hBox.getChildren().add(deleteButton);

			// Add the (title + combo) and the separator in vertical
			categoriesVBox.getChildren().add(hBox);
			categoriesVBox.getChildren().add(new TasksSeparator());
		});
	}

	@Override
	public void delete(String category) {
		// Delete the tasks with that category
		List<Task> tasks = taskRepository.getAll();
		logger.info("Total number of tasks: {}", tasks.size());
		tasks.removeIf(t -> t.getCategory().equals(category));
		logger.info("Total number of tasks after delete: {}", tasks.size());
		taskRepository.store(tasks);

		// Delete the category
		List<Category> categories = getAll();
		logger.info("Total number of categories: {}", categories.size());
		categories.removeIf(c -> c.getName().equals(category));
		logger.info("Total number of categories after delete: {}", categories.size());
		categoryRepository.store(categories);
	}
}
