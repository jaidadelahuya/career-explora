package com.jevalab.azure;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.helper.classes.StringConstants;

public class FreeAccess extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9186497734665816086L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Object o = null;
		HttpSession session = req.getSession();
		AzureUser user = null;
		synchronized (session) {
			o = session.getAttribute(StringConstants.AZURE_USER);
			
		}
		if(o != null) {
			user = (AzureUser) o;
			user.setAuthorized(true);
			user.setValidity("12");
			user.setSubscriptionDate(new Date());
			user.setNewUser(true);
			user.setFreeAccess(true);
			synchronized (session) {
				session.setAttribute(StringConstants.AZURE_USER, user);
			}
			RequestDispatcher rd = req.getRequestDispatcher("/azure/success");
			rd.forward(req, resp);
			
		}
		
		
	}
}
