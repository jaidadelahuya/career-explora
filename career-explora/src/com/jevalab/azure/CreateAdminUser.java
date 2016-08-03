package com.jevalab.azure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jevalab.azure.persistence.AzureUser;

public class CreateAdminUser extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3908355306846714769L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = req.getParameter("user");
		String password = req.getParameter("password");
		
		AzureUser u = new AzureUser();
		
	}

}
