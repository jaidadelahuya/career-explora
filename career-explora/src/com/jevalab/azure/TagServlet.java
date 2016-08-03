package com.jevalab.azure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class TagServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3974667674709699190L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] tags  = {"love","joy","peace"};
		resp.setContentType("application/json");
		Gson gson   = new Gson();
		String s = gson.toJson(tags);
		resp.getWriter().write(s);
	}

}
