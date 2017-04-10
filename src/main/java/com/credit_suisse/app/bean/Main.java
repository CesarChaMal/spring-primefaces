package com.credit_suisse.app.bean;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.credit_suisse.app.dao.TaskDao;

@Component
//@ManagedBean
@ManagedBean(name="main")
//@ManagedBean(name="taskBean", eager = true)
//@SessionScoped
@ApplicationScoped
public class Main {
	
	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	@Autowired
	TaskDao taskDao;

	private List<Task> tasks;

	private Task selectedTask;

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

	public void reset() {
		this.selectedTask = null;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formMain");
    }
	
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Object o = event.getObject();
        if (o.equals(null)) {
        	System.out.println("null");
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
        }
    }
     
    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
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
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    @PostConstruct
    public void setup()  {
		
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