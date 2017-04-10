package com.credit_suisse.app.bean;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.credit_suisse.app.domain.Task;
 
@FacesConverter("taskConverter")
public class TaskConverter implements Converter {
 
	private static final Logger logger = LoggerFactory.getLogger(TaskConverter.class);
	
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
            	TaskBean service = (TaskBean) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("taskBean");
//                logger.info("response: " + service.getTasks().get(Integer.parseInt(value)));
                Task task = service.getTasks().get(Integer.parseInt(value));
                return task;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid task."));
            }
        }
        else {
            return null;
        }
    }
	
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
        	logger.info("response: " + String.valueOf(((Task) object).getId()));
            return String.valueOf(((Task) object).getId());
        }
        else {
            return null;
        }
    }   
}