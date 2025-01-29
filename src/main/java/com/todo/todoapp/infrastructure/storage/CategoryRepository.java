package com.todo.todoapp.infrastructure.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.todo.todoapp.domain.model.Category;

@Repository("CategoryRepository")
public class CategoryRepository implements JsonRepository<Category> {

	private static final Logger logger = LogManager.getLogger(CategoryRepository.class);

	private static final String FILE_NAME = "categories.json";

	@Override
	public List<Category> getAll() {
		
		ObjectMapper objectMapper = new ObjectMapper();

		List<Category> categories = new ArrayList<>();
		try {
			File file = new File(FILE_NAME);
			if (file.exists()) {
				categories = objectMapper.readValue(file, TypeFactory.defaultInstance().constructCollectionType(List.class, Category.class));
			}
		} catch (Exception e) {
			logger.error("Error getting categories from json file. ", e);
		}

		return categories;
	}

	@Override
	public void store(List<Category> categories) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(new File(FILE_NAME), categories);
		} catch (Exception e) {
			logger.error("Error writting categories in json file.", e);
		}
	}
}
