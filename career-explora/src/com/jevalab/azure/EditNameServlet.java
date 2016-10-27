package com.jevalab.azure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.RollbackFailureException;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.WelcomePageBean;

public class EditNameServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		Object o = null;
		synchronized (session) {
			o = session.getAttribute(StringConstants.AZURE_USER);
		}
		
		AzureUser user = null;
		if (o != null && o instanceof AzureUser) {
			user = (AzureUser) o;
		}

		user.setFirstName(req.getParameter("first-name"));
		user.setLastName(req.getParameter("last-name"));
		user.setMiddleName(req.getParameter("middle-name"));
		//user.setDateOfBirth(req.getParameter("date-of-birth"));
		
		
		synchronized (session) {
			session.setAttribute(StringConstants.AZURE_USER, user);
		}
		
		Map<String, String> map = new HashMap<>();
		map.put(StringConstants.FIRSTNAME, req.getParameter("first-name").toUpperCase());
		map.put(StringConstants.LASTNAME, req.getParameter("last-name").toUpperCase());

		resp.setContentType("application/json");
		String json = null;
		json = new Gson().toJson(map).toString();
		System.out.println(json);
		resp.getWriter().write(json);


	}
}
