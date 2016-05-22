package com.jevalab.azure;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.jevalab.azure.persistence.Talent;
import com.jevalab.azure.persistence.TalentCategory;
import com.jevalab.azure.persistence.TalentCategoryJpaController;
import com.jevalab.azure.persistence.TalentJpaController;

public class TalentTestBoardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
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
