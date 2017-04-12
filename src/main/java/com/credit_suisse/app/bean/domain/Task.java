package com.credit_suisse.app.bean.domain;

import java.util.Date;

public class Task {

	private int id;
	private String title;
	private String description;
	private Date due_date;

	public Task() {
		this.id = 0;
		this.title = "";
		this.description = "";
		this.due_date = new Date();
	}

	public Task(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.description = builder.description;
		this.due_date = builder.due_date;
	}
	
	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
	
	
	public Date getDueDate() {
		return due_date;
	}

//	public void setId(int id) {
//		this.id = id;
//	}
//	
//	public void setTitle(String title) {
//		this.title = title;
//	}
//	
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	public void setDueDate(Date due_date) {
//		this.due_date = due_date;
//	}

	public static class Builder {
		
		private int id;
		private String title;
		private String description;
		private Date due_date;
		
		public Builder(){
			this.id = 0;
			this.title = "";
			this.description = "";
			this.due_date = new Date();
		}
		
		public Builder setId(int id){
			this.id = id;
			return this;
		}
		
		public Builder setTitle(String title){
			this.title = title;
			return this;
		}
		
		public Builder setDescription(String description){
			this.description = description;
			return this;
		}
		
		public Builder setDueDate(Date due_date){
			this.due_date = due_date;
			return this;
		}
		
		public Task build(){
			return new Task(this);
		}
	}
	
	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", due_date=" + due_date + "]";
	}

}
