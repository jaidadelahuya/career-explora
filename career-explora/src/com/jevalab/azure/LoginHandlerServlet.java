package com.jevalab.azure;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.exceptions.InvalidLoginException;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class LoginHandlerServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		AzureUser user = Util.authenticateUsername(username);
		
		boolean usingEmail = Util.isEmail(username);
		boolean usingMobile = Util.isNumeric(username);
		
		if(user == null) {
			req.setAttribute(StringConstants.INCORRECT_USERNAME,true);
			if(usingEmail) {
				req.setAttribute(StringConstants.INCORRECT_EMAIL,true);
			} else if (usingMobile) {
				req.setAttribute(StringConstants.INCORRECT_MOBILE,true);
			} 
			throw new InvalidLoginException();
		} else {
			
			if(user.getPassword().equals(password.trim())) {
				HttpSession session = req.getSession();
				synchronized (session) {
					session.setAttribute(StringConstants.AZURE_USER, user);
				}
				
				RequestDispatcher rd = req.getRequestDispatcher("/azure/success");
				rd.forward(req, resp);
			} else {
				req.setAttribute(StringConstants.INCORRECT_PASSWORD,true);
				req.setAttribute(StringConstants.USERNAME,username);
				throw new InvalidLoginException();
			}
		}
	}
}
