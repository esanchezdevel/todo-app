package com.todo.todoapp.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.todo.todoapp.domain.model.Category;

@Service
public class CategoriesServiceImpl implements CategoriesService {
	
	private static final Logger logger = LogManager.getLogger(CategoriesServiceImpl.class);

	private static final String FILE_NAME = "categories.json";

	@Override
	public void store(String category) {
		
		ObjectMapper objectMapper = new ObjectMapper();

		// get already existing categories
		List<Category> categories;
		try {
			File file = new File(FILE_NAME);
			if (file.exists()) {
				categories = objectMapper.readValue(file, TypeFactory.defaultInstance().constructCollectionType(List.class, Category.class));
			} else {
				categories = new ArrayList<>();
			}
		} catch (Exception e) {
			categories = new ArrayList<>();
		}

		// add the new category
		Category categoryEntity = new Category(category);
		categories.add(categoryEntity);

		// store all the categories in the file overwritting it
		try {
			objectMapper.writeValue(new File(FILE_NAME), categories);
		} catch (Exception e) {
			logger.error("Error writting category '" + category + "' in json file.", e);
		}
		
	}
}
