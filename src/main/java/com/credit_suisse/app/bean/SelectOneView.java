package com.credit_suisse.app.bean;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.credit_suisse.app.domain.Task;
 
@ManagedBean
public class SelectOneView {
     
    private String option;   
    private Task task; 
    private List<Task> tasks;
     
    @ManagedProperty("#{taskBean}")
    private TaskBean service;
     
    @PostConstruct
    public void init() {
        tasks = service.getTasks();
    }
 
    public String getOption() {
        return option;
    }
 
    public void setOption(String option) {
        this.option = option;
    }
 
    public Task getTask() {
        return task;
    }
 
    public void setTask(Task task) {
        this.task = task;
    }
 
    public List<Task> getTasks() {
        return tasks;
    }
 
    public void setService(TaskBean service) {
        this.service = service;
    }
}