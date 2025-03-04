package com.todo.todoapp.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.todo.todoapp.application.components.Alerts;
import com.todo.todoapp.application.components.CustomButton;
import com.todo.todoapp.application.components.TasksSeparator;
import com.todo.todoapp.application.exception.AppException;
import com.todo.todoapp.domain.model.Category;
import com.todo.todoapp.domain.model.Task;
import com.todo.todoapp.domain.model.TasksStatus;
import com.todo.todoapp.infrastructure.storage.CategoryRepositoryJpa;
import com.todo.todoapp.infrastructure.storage.TaskRepositoryJpa;

import jakarta.transaction.Transactional;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@Service
public class CategoriesServiceImpl implements CategoriesService {

	private static final Logger logger = LogManager.getLogger(CategoriesServiceImpl.class);

	@Autowired
	private CategoryRepositoryJpa categoryRepository;

	@Autowired
	private TaskRepositoryJpa taskRepository;

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void store(String category) {
		List<Category> categories = categoryRepository.findAll();

		if (categories.stream().anyMatch(c -> c.getName().equalsIgnoreCase(category))) {
			logger.warn("The category '{}' already exists", category);
			return;
		}
		categoryRepository.save(new Category(category));
	}


	@Override
	public List<String> getAllNames() {
		List<Category> categories = categoryRepository.findAll();

		return categories
					.stream()
					.map(Category::getName)
					.collect(Collectors.toList());
	}


	@Override
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> getCategory(String name) throws AppException {
		if (!StringUtils.hasLength(name)) {
			String errorMsg = "Error. Mandatory parameter name not present";
			logger.error(errorMsg);
			throw new AppException(HttpStatus.BAD_REQUEST.value(), errorMsg);
		}
		try {
			return categoryRepository.findByName(name);
		} catch (Exception e) {
			String errorMsg = new StringBuilder("Unexpected error happens retrieving category '")
					.append(name)
					.append("' from database: ")
					.append(e.getMessage()).toString();
			logger.error(errorMsg, e);
			throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorMsg);
		}
	}

	@Override
	public boolean alreadyExists(String category) {
		List<String> categories = getAllNames();
		return categories.stream().anyMatch(c -> c.equalsIgnoreCase(category));
	}


	@Override
	public boolean categoryHasTasks(String category, TasksStatus status, TasksService tasksService) {
		Optional<Category> dbCategory = categoryRepository.findByName(category);

		if (dbCategory.isEmpty()) {
			logger.warn("Category '{}' not found in database", category);
			return false;
		}
		logger.info("Category found: {}", dbCategory.get().getName());
		if (dbCategory.get().getTasks() != null) {
			List<Task> tasks = dbCategory.get().getTasks().stream().filter(t -> t.getStatus() == status).toList();
			return tasks != null && tasks.size() > 0 ? true : false;
		}
		logger.info("Category does not have any task associated");
		return false;
	}


	@Override
	@Transactional
	public void showCategoriesToBeDeleted(VBox categoriesVBox) {
		List<String> categories = getAllNames();

		categoriesVBox.getChildren().clear(); // Clear the view from previous tasks showed
			
		categories.forEach(c -> {
	
			HBox hBox = new HBox();
			hBox.setStyle("-fx-padding: 10px 10px 10px 10px;");

			Label label = new Label();
			label.setText(c);
			label.setMaxWidth(575);
			label.setMinWidth(575);
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
													applicationContext.getBean(CategoriesService.class).delete(c);
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
	@Transactional
	public void delete(String category) {
		Optional<Category> dbCategory = categoryRepository.findByName(category);

		if (dbCategory.isEmpty()) {
			logger.warn("Category '{}' not found in database", category);
			return;
		}

		// Delete the tasks with that category
		dbCategory.get().getTasks().forEach(t -> taskRepository.deleteById(t.getId()));

		// Delete the category
		categoryRepository.deleteById(dbCategory.get().getId());
		logger.info("Category '{}' and all it's tasks deleted", category);
	}
}
