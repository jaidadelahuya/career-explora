package com.jevalab.azure;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jevalab.azure.persistence.PersonalStyle;
import com.jevalab.azure.persistence.PersonalStyleJpa;
import com.jevalab.exceptions.RollbackFailureException;

public class AddPersonalStyleServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String styles = req.getParameter("styles");
		List<String> sty = Arrays.asList(styles.split(","));
		
		PersonalStyle ps = new PersonalStyle(sty);
		
		PersonalStyleJpa cont = new PersonalStyleJpa();
		try {
			cont.create(ps);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resp.setContentType("text/html");
		resp.getWriter().write("<a href='/profile/add-personal-style.html'>Go Back</a>");
	}
}
