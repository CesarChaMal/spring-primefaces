package com.credit_suisse.app.bean;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.credit_suisse.app.bean.domain.Task;
import com.credit_suisse.app.bean.util.FacesMessagesUtil;
import com.credit_suisse.app.dao.TaskDao;

@Component
@ManagedBean(name="taskBean")
@RequestScoped
public class TaskBean implements ManageBeans  {

	private static final Logger logger = LoggerFactory.getLogger(TaskBean.class);

	private int id;
	private String title;
	private String description;
	private Date due_date;
	private Task task;
	
	@Autowired
	private TaskDao taskDao;

	private FacesContext facesContext;

	private RequestContext requestContext;

	public TaskBean() {
//		task = new Task.Builder().build();
		task = new Task();
	}
	
	public int getId() {
		return task.getId();
	}

	public void setId(int id) {
		task = new Task.Builder()
				.setId(id)
				.setTitle(this.task.getTitle())
				.setDescription(this.task.getDescription())
				.setDueDate(this.task.getDueDate())
				.build();
//		this.task.setId(id);
		this.id = id;
	}

	public String getTitle() {
		return  task.getTitle();
	}
	
	public void setTitle(String title) {
		task = new Task.Builder()
				.setId(this.task.getId())
				.setTitle(title)
				.setDescription(this.task.getDescription())
				.setDueDate(this.task.getDueDate())
				.build();
//		this.task.setTitle(title);
		this.title = title;
	}

	public String getDescription() {
		return  task.getDescription();
	}
	
	public void setDescription(String description) {
		task = new Task.Builder()
				.setId(this.task.getId())
				.setTitle(this.task.getTitle())
				.setDescription(description)
				.setDueDate(this.task.getDueDate())
				.build();
//		this.task.setDescription(description);
		this.description = description;
	}
	
	public Date getDueDate() {
		return  task.getDueDate();
	}

	public void setDueDate(Date due_date) {
		task = new Task.Builder()
				.setId(this.task.getId())
				.setTitle(this.task.getTitle())
				.setDescription(this.task.getDescription())
				.setDueDate(due_date)
				.build();
//		this.task.setDueDate(due_date);
		this.due_date = due_date;
	}
	
	@Override
	public void reset() {
		this.task = null;
	}

	@Override
	public void save() {
    	taskDao.save(this.task);
    	FacesMessagesUtil.addMessage(facesContext, "Deleted", "Task Deleted with id " + id);
    	this.reset();
	}

	@Override
	public void delete() {
    	taskDao.deleteById(this.id);
    	FacesMessagesUtil.addMessage(facesContext, "Deleted", "Task Deleted with id " + id);
    	this.reset();
	}

    @PostConstruct
    public void setup()  {
    	facesContext = FacesContext.getCurrentInstance();
        requestContext = RequestContext.getCurrentInstance();
   }
    
}

