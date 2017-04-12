package com.credit_suisse.app.bean.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesMessagesUtil {
	
	public static void addMessage(FacesContext facesContext, String summary, String detail) {
	    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
	    facesContext.addMessage(null, message);
	}
	
}

