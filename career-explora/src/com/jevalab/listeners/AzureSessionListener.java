package com.jevalab.listeners;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.helper.classes.IpnInfo;
import com.jevalab.helper.classes.LoginHelper;
import com.jevalab.helper.classes.StringConstants;

public class AzureSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent e) {
		LoginHelper.checkIdSequence();
		//what happens when server goes down?
		Set<IpnInfo> paypalTransactionIds = new HashSet<>();
		e.getSession().setAttribute(StringConstants.PAYPAL_TRANSACTION_IDS, paypalTransactionIds);
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		 
		HttpSession session = e.getSession();
		Object o = session.getAttribute(StringConstants.AZURE_USER);
		AzureUser user = null;
		if(o!=null) {
			user = (AzureUser) o;
			LoginHelper.persistUser(user, null);
		}	
	}

}
