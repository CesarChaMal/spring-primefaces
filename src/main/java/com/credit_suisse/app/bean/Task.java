package com.credit_suisse.app.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.credit_suisse.app.dao.TaskDao;

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

/*	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	@Autowired
	TaskDao taskDao;

	private FacesContext facesContext;

	private RequestContext requestContext;

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public void save() {
       	facesContext = FacesContext.getCurrentInstance();
    	Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
    	String title = params.get("title");    	
    	String description = params.get("description");    	
    	String due_dateStr = params.get("due_date");    	
        Date due_date = new Date();
    	
    	DateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy");
    	try {
            due_date = formatter.parse(due_dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    	
    	taskDao.save(title, description, due_date);
    	this.reset();
    }

	public void reset() {
        requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formMain");
    }
*/	
     
	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", description=" + description + ", due_date=" + due_date + "]";
	}

}
