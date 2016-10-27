/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.careerplus;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jevalab.azure.persistence.Talent;
import com.jevalab.azure.persistence.TalentJpaController;

public class TalentTestBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String msg = req.getParameter("test");

		TalentJpaController cont = new TalentJpaController();
		List<Talent> talents = cont.findTalentEntities(msg);

		String json = null;
		json = new Gson().toJson(talents).toString();
		resp.setContentType("application/json");
		System.out.println(json);
		resp.getWriter().write(json);
	}
}