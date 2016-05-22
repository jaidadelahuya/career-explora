package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.appengine.api.datastore.KeyFactory;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.Record;
import com.jevalab.azure.persistence.RecordJpaController;
import com.jevalab.azure.persistence.SkillCategory;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.RollbackFailureException;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.UserProfile;
import com.jevalab.helper.classes.Util;

public class SaveSkillsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		if(!session.isNew()) {
			String hb = req.getParameter("hb");
			String tb = req.getParameter("tb");
			String wb = req.getParameter("wb");
			String nd = req.getParameter("nd");
			String testDate = req.getParameter("testDate");
			
			List<String> shb = Util.asList(hb);
			List<String> stb = Util.asList(tb);
			List<String> swb = Util.asList(wb);
			List<String> snd = Util.asList(nd);
			
			Map<String, String> allSkillRecords = new TreeMap<>();
			allSkillRecords = Util.addToSkillRecords(shb, SkillCategory.HAVE_BUILT,
					allSkillRecords);
			allSkillRecords = Util.addToSkillRecords(stb, SkillCategory.TO_BUILD,
					allSkillRecords);
			allSkillRecords = Util.addToSkillRecords(swb, SkillCategory.WONT_BUILD,
					allSkillRecords);
			allSkillRecords = Util.addToSkillRecords(snd, SkillCategory.NOT_DECIDED,
					allSkillRecords);
			
			boolean updated = Util.updateSkill(session, StringConstants.SKILL_BUILDER, allSkillRecords, testDate);
			
			UserProfile up = null;
			if (updated) {

				up = Util.getUserProfileFromSession(session);
				List<String> skillToLearnList = up.getSkillToLearn();
				List<String> learntSkillsList = up.getLearntSkills();
				Map<String, Object> map = new HashMap<>();

				if (skillToLearnList.size() > 6) {
					skillToLearnList = up.getSkillToLearn().subList(0, 6);
					map.put("hasMoreSkillToLearn", true);
				} else {
					map.put("hasMoreSkillToLearn", false);
				}
				
				map.put("skillToLearn", skillToLearnList);
				
				if (learntSkillsList.size() > 6) {
					learntSkillsList = up.getLearntSkills().subList(0, 6);
					map.put("hasMorelearntSkill", true);
				} else {
					map.put("hasMorelearntSkill", false);
				}

				map.put("learntSkills", learntSkillsList);
				if(up.isCurrentUser()) {
					Util.useJSON(map, resp);
				}
				

			} else {
				resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,
						"We could not save your test on the server. Please try again.");
			}
		}
		
	}
	
}
