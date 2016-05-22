package com.jevalab.azure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jevalab.azure.persistence.CareerValues;
import com.jevalab.azure.persistence.CareerValuesJpaController;

public class CareerValuesServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		CareerValuesJpaController cont = new CareerValuesJpaController();
		List<CareerValues> cvs = cont.findCareerValuesEntities();
		CareerValues cv = cvs.get(0);
		
		String json = null;
		json = new Gson().toJson(cv).toString();
		resp.setContentType("application/json");
		System.out.println(json);
		resp.getWriter().write(json);

	}

}
