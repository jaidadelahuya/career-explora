package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.jevalab.azure.persistence.Question;
import com.jevalab.azure.persistence.QuestionJpaController;
import com.jevalab.exceptions.RollbackFailureException;

public class QuestionInputServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Question q = new Question();
		String subjectName = req.getParameter("subject-name");
		subjectName = subjectName.trim().toLowerCase();
		q.setSubjectName(subjectName);
		q.setBody(req.getParameter("body"));
		q.setExplanation(req.getParameter("explanation"));
		q.setVendor(req.getParameter("vendor"));
		String year = req.getParameter("year");
		q.setYear(year);
		q.setCorrectAlternative(req.getParameter("correct-alt"));
		List<String> alts = new ArrayList<String>();
		if(req.getParameter("alt-a")!=null) {
			alts.add(req.getParameter("alt-a"));
		}
		if(req.getParameter("alt-b")!=null) {
			alts.add(req.getParameter("alt-b"));
		}
		
		if(req.getParameter("alt-c")!=null) {
			alts.add(req.getParameter("alt-c"));
		}
		
		if(req.getParameter("alt-d")!=null) {
			alts.add(req.getParameter("alt-d"));
		}
		
		if(req.getParameter("alt-e")!=null) {
			alts.add(req.getParameter("alt-e"));
		}
		
		q.setAlternatives(alts);
		String pUrl = req.getParameter("picture-url");
		String url = "/images/"+subjectName+"/"+year+"/"+pUrl;
		q.setPictureUrl(url);
		
		q.setCategoryName(req.getParameter("category-name"));

		
		QuestionJpaController cont = new QuestionJpaController();
		try {
			cont.create(q);
		} catch (RollbackFailureException e) {
			resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			e.printStackTrace();
		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
			e.printStackTrace();
		}

		
	}
}
