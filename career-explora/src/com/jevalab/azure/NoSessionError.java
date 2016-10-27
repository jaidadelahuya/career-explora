/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure;

import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoSessionError extends HttpServlet {
	private static final long serialVersionUID = -2318133297247645868L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String headerName = req.getHeader("x-requested-with");
		if ("XMLHttpRequest".equalsIgnoreCase(headerName)) {
			resp.setContentType("application/json");
			resp.getWriter().write(new Gson().toJson("/"));
		} else {
			resp.sendRedirect("/");
		}
	}
}