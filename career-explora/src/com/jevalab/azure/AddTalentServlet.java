package com.jevalab.azure;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.jevalab.azure.persistence.Skill;
import com.jevalab.azure.persistence.SkillCluster;
import com.jevalab.azure.persistence.SkillClusterJpaController;
import com.jevalab.azure.persistence.SkillJpaController;
import com.jevalab.azure.persistence.Talent;
import com.jevalab.azure.persistence.TalentCategory;
import com.jevalab.azure.persistence.TalentCategoryJpaController;
import com.jevalab.azure.persistence.TalentCluster;
import com.jevalab.azure.persistence.TalentClusterJpaController;
import com.jevalab.azure.persistence.TalentJpaController;
import com.jevalab.azure.persistence.TalentSkill;
import com.jevalab.azure.persistence.TalentSkillJpaController;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.PreexistingEntityException;
import com.jevalab.exceptions.RollbackFailureException;

public class AddTalentServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String talentName = req.getParameter("talent-name");
		String category = req.getParameter("category");
		String[] slns = req.getParameterValues("skill-name");
		String[] ccs = req.getParameterValues("cluster-name");
		String[] descs = req.getParameterValues("skill-description");
		String[] questions = req.getParameterValues("questions");
		List<String> quests = Arrays.asList(questions);
		
		Key talentCategoryKey = KeyFactory.createKey(TalentCategory.class.getSimpleName(), category);
		TalentCategoryJpaController talentCategoryCont = new TalentCategoryJpaController();
		TalentCategory talentCategory = talentCategoryCont.findTalentCategory(talentCategoryKey);
		if(talentCategory == null) {
			try {
				Set<Talent> ts = new HashSet<Talent>();
				Talent t = new Talent(talentName);
				t.setQuestions(quests);
				t.setCategory(category);
				ts.add(t);
				talentCategory = new TalentCategory(category, ts);
				talentCategoryCont.create(talentCategory);
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
		} else {
			Set<Talent> ts = talentCategory.getTalent();
			Talent t = new Talent(talentName);
			t.setQuestions(quests);
			t.setCategory(talentCategory.getCategory());
			ts.add(t);
			try {
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
		
		
		Skill skill = null;
		TalentSkill ts = null;
		SkillCluster sc = null;
		TalentCluster tc = null;
		TalentSkill ts1 = null;
		SkillCluster sc1 = null;
		TalentCluster tc1 = null;
		TalentClusterJpaController tcCont = new TalentClusterJpaController();
		TalentSkillJpaController tsCont = new TalentSkillJpaController();
		SkillClusterJpaController scCont = new SkillClusterJpaController();
		SkillJpaController sCont = new SkillJpaController();
		TalentJpaController tCont = new TalentJpaController();
		
		for(int i=0; i < ccs.length; i++) {
			
			skill = new Skill(slns[i], new Text(descs[i]));
			if(sCont.findSkill(skill.getKey())== null) {
				try {
					sCont.create(skill);
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
			}
			
			
			ts = new TalentSkill(talentName, slns[i]);
			sc = new SkillCluster(ccs[i], slns[i]);
			tc = new TalentCluster(ccs[i], talentName);
			
			tc1 = tcCont.findTalentCluster(tc.getKey());
			if(tc1 == null) {
				try {
					tcCont.create(tc);
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
			}
			
			ts1 = tsCont.findTalentSkill(ts.getKey());
			if(ts1 == null) {
				try {
					tsCont.create(ts);
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
			}
			
			sc1 = scCont.findSkillCluster(sc.getKey());
			if(sc1 == null) {
				try {
					scCont.create(sc);
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
			}
			
		}
		
		
		
	}
}
