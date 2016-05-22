package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.Record;
import com.jevalab.azure.persistence.RecordJpaController;
import com.jevalab.azure.persistence.Skill;
import com.jevalab.azure.persistence.SkillJpaController;
import com.jevalab.azure.persistence.TalentSkillJpaController;
import com.jevalab.helper.classes.StringConstants;


public class SkillBuilderServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		AzureUser user = (AzureUser) req.getSession(false).getAttribute(
				"azureUser");
		String id = user.getUserID();

		Map<String, String> talents = getTalentsMap(id);
		if (talents == null) {
			resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,
					"You have not taken the Talent Hunt test");
			return;
		} else {
			Map<String, List<String>> cTalents = categorizeTalents(talents);
			Map<String, List<String>> vStrongTalentSkillNames = mapTalentToSkillNames(cTalents
					.get("VS"));
			Map<String, List<String>> strongTalentSkillNames = mapTalentToSkillNames(cTalents
					.get("S"));
			List<UserSkill> vStrongTalentSkills = mapTalentToSkill(vStrongTalentSkillNames);
			List<UserSkill> strongTalentSkills = mapTalentToSkill(strongTalentSkillNames);
			Map<String, List<UserSkill>> fMap = new HashMap<>();
			fMap.put("VS", vStrongTalentSkills);
			fMap.put("S", strongTalentSkills);

			String json = "";
			json = new Gson().toJson(fMap).toString();

			resp.setContentType("application/json");
			System.out.println(json);
			resp.getWriter().write(json);
		}

	}

	private List<UserSkill> mapTalentToSkill(Map<String, List<String>> vstsn) {

		List<UserSkill> skills = new ArrayList<>();

		List<String> skns = null;
		Skill skill = null;
		UserSkill uSkill = null;

		SkillJpaController c1 = new SkillJpaController();
		Set<String> talents = vstsn.keySet();

		for (String talent : talents) {
			skns = vstsn.get(talent);

			for (String s : skns) {
				skill = new Skill(s);
				skill = c1.findSkill(skill.getKey());
				if (skill != null) {
					uSkill = new UserSkill(skill.getName(), skill
							.getDescription().getValue(), talent);
					skills.add(uSkill);
				}
			}

		}
		return skills;
	}

	private Map<String, List<String>> mapTalentToSkillNames(
			List<String> talentNames) {

		Map<String, List<String>> map = new TreeMap<>();
		List<String> skills = null;

		TalentSkillJpaController c1 = new TalentSkillJpaController();

		for (String talentName : talentNames) {
			skills = c1.findSkills(talentName);
			map.put(talentName, skills);
		}

		return map;
	}

	private Map<String, List<String>> categorizeTalents(
			Map<String, String> talents) {

		List<String> strongtalents = new ArrayList<>();
		List<String> veryStrongtalents = new ArrayList<>();
		Map<String, List<String>> map = new HashMap<>();

		Set<String> keys = talents.keySet();
		for (String s : keys) {
			String v = talents.get(s);
			int u = Integer.parseInt(v);
			if (u >= 8) {
				veryStrongtalents.add(s);
			} else if (u >= 6 && u < 8) {
				strongtalents.add(s);
			}
		}
		map.put("VS", veryStrongtalents);
		map.put("S", strongtalents);
		System.out.println(map);
		return map;
	}

	private Map<String, String> getTalentsMap(String id) {

		Record r1 = new Record(id, StringConstants.TALENT_HUNT);
		Key talentKey = r1.getKey();
		RecordJpaController cont = new RecordJpaController();
		r1 = cont.findRecord(talentKey);
		Map<String, String> talents = null;
		if (r1 != null) {
			talents = r1.getTalents();

		}
		System.out.println(talents);
		return talents;
	}
}
