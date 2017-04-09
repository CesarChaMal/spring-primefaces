package com.credit_suisse.app.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.credit_suisse.app.bean.TaskBean;

public class MyWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//public class MyWebInitializer implements WebApplicationInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { SpringRootConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { SpringWebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	public void onStartup(ServletContext servletContext) throws ServletException {
//		super.onStartup(servletContext);
//		servletContext.setInitParameter("spring.profiles.active", "hsql");
//		servletContext.setInitParameter("spring.profiles.active", "derby");
		servletContext.setInitParameter("spring.profiles.active", "h2");
	}

}