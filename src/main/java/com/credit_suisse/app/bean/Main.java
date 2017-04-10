package com.credit_suisse.app.bean;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.credit_suisse.app.dao.TaskDao;

@Component
//@ManagedBean
@ManagedBean(name="main")
//@ManagedBean(name="main", eager = true)
@ApplicationScoped
public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	@Autowired
	TaskDao taskDao;

	private List<Task> tasks;

	@ManagedProperty("#{task}")
	private Task task;
	
	private Task selectedTask;

	private FacesContext facesContext;

	private RequestContext requestContext;

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
    public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Task getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(Task task) {
		this.selectedTask = task;
	}

	public Task getTask() {
		return task;
	}
	
	public void setTask(Task task) {
		this.task = task;
	}
	
	public void reset() {
		this.selectedTask = null;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formMain");
    }
	
    public void onDateSelect(SelectEvent event) {
        facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Object o = event.getObject();
        if (o.equals(null)) {
        	System.out.println("null");
        	facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
        }
    }
     
    public void click() {
        requestContext = RequestContext.getCurrentInstance();
        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }
	
    public void save() {
       	facesContext = FacesContext.getCurrentInstance();
    	task = (Task) facesContext.getApplication().evaluateExpressionGet(facesContext, "#{task}", Task.class);
		taskDao.save(task);
        addMessage("Saved", "Task Saved with title " + task.getTitle());
        this.refresh();
        this.task = null;
    }
     
    public void save(String title, String description, Date due_date) {
    	System.out.println(title);
    	System.out.println(description);
    	System.out.println(due_date);
    	
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.format(due_date);
        System.out.println(due_date);
        
    	taskDao.save(title, description, due_date);
    	addMessage("Saved", "Task Saved with title " + task.getTitle());
    	this.refresh();
    	this.task = null;
    }
    
    public void delete() {
    	int id = getSelectedTask().getId(); 
    	taskDao.deleteById(id);
    	addMessage("Deleted", "Task Deleted with id " + id);
    	this.refresh();
    	this.reset();
    }
    
    public void refresh() {
		tasks = taskDao.findAll();
    }
    
    public void addMessage(String summary, String detail) {
    	facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        facesContext.addMessage(null, message);
    }
    
    @PostConstruct
    public void setup()  {

    	facesContext = FacesContext.getCurrentInstance();
        requestContext = RequestContext.getCurrentInstance();
   			
		Task task = new Task();
		task.setTitle("test task");
		task.setDescription("test description");
		task.setDueDate(new Date());
		
		taskDao.save(task);
		taskDao.save("test task 2", "description", new Date());
		taskDao.save("test task 2", "description2", new Date());
		
//		Task foundTask = taskDao.findById(1);
//		taskDao.delete(foundTask);
//
//		taskDao.deleteById(2);
//		
//		taskDao.deleteByTitle("task3");
//		taskDao.deleteByTitle("test task 2");
		
		tasks = taskDao.findAll();
		logger.debug(Arrays.toString(tasks.toArray()));
   }
}