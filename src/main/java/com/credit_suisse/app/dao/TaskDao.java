package com.credit_suisse.app.dao;

import java.util.List;

import com.credit_suisse.app.domain.Task;

public interface TaskDao {

	Task findById(int id);

	Task findByTitle(String title);
	
	List<Task> findByTitleList(String title);
	
	List<Task> findAll();

	void setTitle(int id, String title);
}