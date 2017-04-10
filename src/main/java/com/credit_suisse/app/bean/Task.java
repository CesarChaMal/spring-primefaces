package com.credit_suisse.app.bean;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@ManagedBean(name="task", eager = true)
@RequestScoped
//@ManagedBean(name="task")
public class Task {

	private int id;
	private String title;
	private String description;
	private Date due_date;

	public Task(int id, String title, String description, Date due_date) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.due_date = due_date;
	}

	public Task() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
