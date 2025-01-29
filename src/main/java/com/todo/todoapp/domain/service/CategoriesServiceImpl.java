package com.todo.todoapp.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.todo.todoapp.domain.model.Category;
import com.todo.todoapp.infrastructure.storage.JsonRepository;

@Service
public class CategoriesServiceImpl implements CategoriesService {

	@Autowired
	@Qualifier("CategoryRepository")
	private JsonRepository<Category> categoryRepository;

	@Override
	public void store(String category) {
		// get already existing categories
		List<Category> categories = categoryRepository.getAll();

		// add the new category
		categories.add(new Category(category));

		// store all the categories in the file overwritting it
		categoryRepository.store(categories);		
	}
}
