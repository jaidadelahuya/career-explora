package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.jevalab.azure.persistence.CareerCluster;
import com.jevalab.azure.persistence.CareerClusterJpaController;
import com.jevalab.azure.persistence.CareerPathway;
import com.jevalab.exceptions.PreexistingEntityException;
import com.jevalab.exceptions.RollbackFailureException;


public class AddCareerClusterServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String clusterName = req.getParameter("cluster-name");
		String description = req.getParameter("description");
		
		String quest = req.getParameter("questions");
		String[] questions = quest.split("@");
		
		String[] pathwayNames = req.getParameterValues("pathway-name");
		String[] careerLists = req.getParameterValues("careers");
		
		Key key = null;
		CareerPathway cp = null;
		List<CareerPathway> pathways = new ArrayList<>();
		
		for(int i = 0; i < pathwayNames.length; i++) {
			List<String> careers = getCareers(careerLists[i]);
			key = KeyFactory.createKey(CareerPathway.class.getSimpleName(), pathwayNames[i]);
			cp = new CareerPathway(key, pathwayNames[i], careers); 
			pathways.add(cp);
		}
		
		Text descriptionText = new Text(description);
		CareerCluster cc = new CareerCluster();
		cc.setClusterName(clusterName);
		cc.setDescription(descriptionText);
		cc.setQuestions(Arrays.asList(questions));
		cc.setPathway(pathways);
		
		CareerClusterJpaController cont = new CareerClusterJpaController();
		try {
			cont.create(cc);
		} catch (PreexistingEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resp.getWriter().write("cluster name: "+clusterName);
		resp.getWriter().write("description: "+description);
		resp.getWriter().write("questions:"+questions);
		
	}

	private List<String> getCareers(String careerLists) {
		String[] careers = careerLists.split(";");
		return Arrays.asList(careers);
	}
}
