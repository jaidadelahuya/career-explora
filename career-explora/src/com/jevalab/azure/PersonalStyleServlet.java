package com.jevalab.azure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jevalab.azure.persistence.PersonalStyle;
import com.jevalab.azure.persistence.PersonalStyleJpa;

public class PersonalStyleServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		PersonalStyleJpa cont = new PersonalStyleJpa();
		PersonalStyle ps = cont.findPersonalStyle(1l);
		List<String> styles = ps.getStyles();
		
		
		String json = null;
		json = new Gson().toJson(styles).toString();
		resp.setContentType("application/json");
		System.out.println(json);
		resp.getWriter().write(json);
	}

}
