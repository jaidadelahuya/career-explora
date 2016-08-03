package com.jevalab.azure.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.helper.classes.EntityConverter;
import com.jevalab.helper.classes.Util;

public class CreateUser extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3143811886778308047L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String firstName = req.getParameter("first-name");
		String lastName = req.getParameter("last-name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		HttpSession session = req.getSession();
		if(!Util.notNull(firstName, lastName, email, password)) {
			synchronized (session) {
				session.removeAttribute("formSuccess");
				session.setAttribute("formError", "fill out the form completely");
			}
		}else {
			AzureUser a = new AzureUser();
			a.setFirstName(firstName);
			a.setLastName(lastName);
			a.setEmail(email);
			a.setPassword(password);
			a.setValidity("ADMIN");
			a.setAuthorized(true);
			
			
			UserJpaController cont = new UserJpaController();
			cont.create(a, null);
			
			synchronized (session) {
				session.removeAttribute("formError");
				session.setAttribute("formSuccess", "User Created");
			}
		}
		
		
		
		resp.sendRedirect(resp.encodeRedirectURL("/ca/admin/user/form/create"));
	}

}
