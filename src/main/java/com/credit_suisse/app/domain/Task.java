package com.credit_suisse.app.domain;

import java.util.Date;

public class Task {

	private Long id;
	private String title;
	private String description;
	private Date due_date;

	public Task(Long id, String title, String description, Date due_date) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.due_date = due_date;
	}

	public Task() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getDueDate() {
		return due_date;
	}

	public void setDueDate(Date due_date) {
		this.due_date = due_date;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", due_date=" + due_date + "]";
	}

}
