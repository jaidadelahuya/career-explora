package com.jevalab.azure;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.helper.classes.StringConstants;

public class EditUsernameServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String uri = req.getRequestURI();
		uri = uri.substring(uri.indexOf('-')+1);
		System.out.println(uri);
		if(uri.equalsIgnoreCase("mobile")) {
			req.setAttribute(StringConstants.USING_MOBILE, true);
		} else if(uri.equalsIgnoreCase("email")) {
			req.setAttribute(StringConstants.USING_EMAIL, true);
		}
		req.setAttribute(StringConstants.EDITING_USERNAME, true);
		RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
		rd.forward(req, resp);
	}
}
