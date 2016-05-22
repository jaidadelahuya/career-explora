package com.jevalab.azure;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jevalab.azure.persistence.Talent;
import com.jevalab.azure.persistence.TalentCategory;
import com.jevalab.azure.persistence.TalentCategoryJpaController;
import com.jevalab.azure.persistence.TalentJpaController;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.RollbackFailureException;

public class EditTalentServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("talent-name");
		String[] questions = req.getParameterValues("questions");
		String category = req.getParameter("category");
		
		
		Key talentCategoryKey = KeyFactory.createKey(TalentCategory.class.getSimpleName(), category);
		TalentCategoryJpaController talentCategoryCont = new TalentCategoryJpaController();
		TalentCategory talentCategory = talentCategoryCont.findTalentCategory(talentCategoryKey);
		
		if(talentCategory != null) {
			
			try {
				List<String> quests = Arrays.asList(questions);
				Set<Talent> ts = talentCategory.getTalent();
				Talent talent = new Talent(name);
				talent.setQuestions(quests);
				ts.add(talent);
				talentCategoryCont.edit(talentCategory);
			} catch (NonexistentEntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RollbackFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
		
	}
}
