package com.credit_suisse.app.bean;

import java.util.Date;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

public interface NewTaskBehaviour {
    public void onDateSelect(SelectEvent event);
    public void click();
	public void save();
	public void save(String title, String description, Date due_date);
	public void delete();
}
