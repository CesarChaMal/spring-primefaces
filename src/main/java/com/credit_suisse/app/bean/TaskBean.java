package com.credit_suisse.app.bean;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.credit_suisse.app.dao.TaskDao;
import com.credit_suisse.app.domain.Task;

@Component
//@ManagedBean
@ManagedBean(name="taskBean")
//@ManagedBean(name="taskBean", eager = true)
//@SessionScoped
@ApplicationScoped
public class TaskBean {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskBean.class);

	@Autowired
	TaskDao taskDao;

	private List<Task> tasks;

	private Task task;

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
    public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Task getTask() {
		return task;
	}

	public Task searchTask(int id) {
		return this.taskDao.findById(id);
	}
	
	public String searchTask() {
		return "task";
	}
	
	public void setTask(Task task) {
		this.task = task;
	}

	@PostConstruct
    public void setup()  {
		tasks = taskDao.findAll();
		logger.debug(Arrays.toString(tasks.toArray()));
   }
}