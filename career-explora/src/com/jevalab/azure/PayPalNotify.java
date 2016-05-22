package com.jevalab.azure;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class PayPalNotify extends HttpServlet {

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setStatus(HttpServletResponse.SC_OK);
		//should be async
		boolean complete = Util.sendPayLoad(req);
		if(complete) {
			HttpSession session = req.getSession();
			Object o = null;
			AzureUser user = null;
			synchronized (session) {
				o = session.getAttribute(StringConstants.AZURE_USER);
			}
			if(o!=null) {
				user = (AzureUser) o;
				user.setAuthorized(true);
				user.setValidity("12");
				user.setSubscriptionDate(new Date());
				user.setNewUser(true);
				
				synchronized (session) {
					session.setAttribute(StringConstants.AZURE_USER, user);
				}
			}
		}
		
		
	}
}
