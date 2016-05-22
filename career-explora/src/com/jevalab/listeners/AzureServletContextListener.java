package com.jevalab.listeners;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.jevalab.azure.persistence.IdSequence;
import com.jevalab.azure.persistence.IdSequenceJpaController;
import com.jevalab.exceptions.RollbackFailureException;

public class AzureServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

	}

}
