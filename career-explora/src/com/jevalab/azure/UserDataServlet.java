package com.jevalab.azure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class UserDataServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//JsonUser jUser = null;
		try {
			//jUser = (JsonUser)req.getSession(false).getAttribute("jUser");
		} catch (NullPointerException npe) {
			resp.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT,
					"Your session is invalid or has expired. Please Login again.");
		}
		resp.setContentType("application/json");
		

		String json = null;
		//json = new Gson().toJson(jUser).toString();
		System.out.println(json);
		resp.getWriter().write(json);

	}

	
}
