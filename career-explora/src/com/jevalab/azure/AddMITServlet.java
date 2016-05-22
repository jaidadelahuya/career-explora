package com.jevalab.azure;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jevalab.azure.persistence.MultipleIntelligenceTestQuestion;
import com.jevalab.azure.persistence.MultipleIntelligenceTestQuestionJpaController;
import com.jevalab.exceptions.RollbackFailureException;

public class AddMITServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String intelligenceType = req.getParameter("intelligence-type");
		String questionString = req.getParameter("question");
		String shortDescription = req.getParameter("short-description");
		String longDescription = req.getParameter("long-description");
		String typicalRoles = req.getParameter("typical-roles");
		String relatedActivities = req.getParameter("related-activities");
		String preferredLearningStyle = req.getParameter("preferred-learning-style");
		
		List<String> questions = Arrays.asList(questionString.split(","));
		
		MultipleIntelligenceTestQuestionJpaController cont = new MultipleIntelligenceTestQuestionJpaController();
		MultipleIntelligenceTestQuestion mitq = new MultipleIntelligenceTestQuestion();
		mitq.setIntelligenceType(intelligenceType);
		mitq.setQuestions(questions);
		mitq.setShortDescription(shortDescription);
		mitq.setLongDescription(longDescription);
		mitq.setTypicalRoles(typicalRoles);
		mitq.setRelatedActivities(relatedActivities);
		mitq.setPreferredLearningStyle(preferredLearningStyle);
		try {
			cont.create(mitq);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
