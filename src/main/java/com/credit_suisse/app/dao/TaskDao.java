package com.credit_suisse.app.dao;

import java.util.List;

import com.credit_suisse.app.bean.domain.Task;

public interface TaskDao {

	Task findById(int id);

	Task findByTitle(String title);
	
	List<Task> findByTitleList(String title);
	
	List<Task> findAll();

	void updateTitle(int id, String title);

	void save(Task task);
	
	void delete(Task task);
	
	void deleteById(int id);
	
	void deleteByTitle(String title);
}