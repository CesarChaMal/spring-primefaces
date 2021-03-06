package com.credit_suisse.app.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.credit_suisse.app.bean.domain.Task;
import com.credit_suisse.app.bean.util.FacesMessagesUtil;
import com.credit_suisse.app.dao.TaskDao;

@Component
@ManagedBean(eager=true, name="mainBean")
@SessionScoped
public class MainBean implements ManageBeans {
	
	private static final Logger logger = LoggerFactory.getLogger(MainBean.class);

	@Autowired
	private TaskDao taskDao;

	private List<Task> tasks;

//	@ManagedProperty("#{task}")
//	private Task task;
	
	private Task selectedTask;

	private FacesContext facesContext;

	private RequestContext requestContext;

    public List<Task> getTasks() {
		return Collections.unmodifiableList(tasks);
	}

//    public void setTasks(List<Task> tasks) {
//    	this.tasks = tasks;
//    }
    
	public Task getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(Task selectedTask) {
		this.selectedTask = selectedTask;
	}

	public void reset() {
		this.selectedTask = null;
        requestContext = RequestContext.getCurrentInstance();
        requestContext.reset("formMain:panel");
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
    	Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
    	String title = params.get("formMain:newTitle");    	
    	String description = params.get("formMain:newDescription");    	
    	String due_dateStr = params.get("formMain:newDueDate_input");    	
    	
    	logger.debug("title: " + title);
    	logger.debug("description: " + description);
    	logger.debug("due_date: " + due_dateStr);

    	Date due_date = new Date();
    	
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	try {
            due_date = formatter.parse(due_dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    	
//    	Task task = new Task();
//    	task.setTitle(title);
//    	task.setDescription(description);
//    	task.setDueDate(due_date);
    	
    	taskDao.save(new Task.Builder().setTitle(title).setDescription(description).setDueDate(due_date).build());
//    	taskDao.save(task);
    	facesContext = FacesContext.getCurrentInstance();
    	FacesMessagesUtil.addMessage(facesContext, "Saved", "Task Saved with title " + title);
    	this.refresh();
    	this.reset();
	}
    	
    public void delete() {
    	int id = getSelectedTask().getId(); 
    	taskDao.deleteById(id);
    	facesContext = FacesContext.getCurrentInstance();
    	FacesMessagesUtil.addMessage(facesContext, "Deleted", "Task Deleted with id " + id);
    	this.refresh();
    	this.reset();
    }
    
    public void refresh() {
		tasks = taskDao.findAll();
    }
    
    @PostConstruct
    public void setup()  {

    	facesContext = FacesContext.getCurrentInstance();
        requestContext = RequestContext.getCurrentInstance();
   			
//		Task task = new Task();
//		task.setTitle("test task");
//		task.setDescription("test description");
//		task.setDueDate(new Date());
		
//		taskDao.save(task);
//		taskDao.save("test task 2", "description", new Date());
//		taskDao.save("test task 2", "description2", new Date());
		
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
