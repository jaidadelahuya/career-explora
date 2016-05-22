package com.jevalab.azure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jevalab.azure.persistence.MultipleIntelligenceTestQuestion;
import com.jevalab.azure.persistence.MultipleIntelligenceTestQuestionJpaController;

public class GetMultipleIntelligenceTypeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
			MultipleIntelligenceTestQuestionJpaController cont = new MultipleIntelligenceTestQuestionJpaController();
			List<MultipleIntelligenceTestQuestion> mit = cont.findAllMultipleIntelligenceTestQuestionEntities();
			
			String json = null;
			json = new Gson().toJson(mit).toString();
			
			resp.setContentType("application/json");
			System.out.println(json);
			resp.getWriter().write(json);
		
		
	}
}
