package com.jevalab.helper.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.appengine.api.datastore.Text;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.Record;
import com.jevalab.azure.persistence.RecordJpaController;
import com.jevalab.azure.persistence.SkillCategory;
import com.jevalab.azure.persistence.UserJpaController;

public class ProfileHelper {

	public static AzureUser getProfileUser(String id) {
		UserJpaController cont = new UserJpaController();
		AzureUser user = cont.findUser(id);
		return user;
	}

	public static UserProfile getProfileData(AzureUser user) {

		UserProfile profile = new UserProfile();
		profile.setFirstName(user.getFirstName());
		profile.setLastName(user.getLastName());
		profile.setMiddleName(user.getMiddleName());
		profile.setDateOfBirth(user.getDateOfBirth());
		profile.setGender(user.getGender());
		profile.setEmail(user.getEmail());
		profile.setSchool(user.getSchool());
		profile.setState(user.getState());
		profile.setCountry(user.getCountry());
		profile.setCover(user.getCover());
		profile.setPicture(user.getPicture());
		profile.setAttends(user.isAttends());
		profile.setLastSeenDate(user.getLastSeenDate().toString());

		profile.setStyles(getStyles(user.getUserID()));
		profile.setValues(getValues(user.getUserID()));
		profile.setClusters(getClusters(user.getUserID()));
		profile.setTalents(getTalents(user.getUserID()));
		String mitsValues = getMits(user.getUserID());
		profile.setMits(getAllStrongMits(mitsValues));

		Map<String, String> allSkills = getAllSkills(user.getUserID());
		profile.setSkillToLearn(getSkillsToLearn(allSkills));
		profile.setLearntSkills(getLearntSkills(allSkills));

		return profile;
	}

	private static List<String> getStyles(String id) {
		List<String> list = new ArrayList<>();
		Record rec = new Record(id, StringConstants.PERSONAL_STYLE);
		RecordJpaController cont = new RecordJpaController();
		rec = cont.findRecord(rec.getKey());
		if (rec == null) {
			return list;
		} else {
			list = asStylesList(rec.getStyles());
			return list;
		}

	}

	public static List<String> asStylesList(Text sty) {
		List<String> list = new ArrayList<>();
		String styles = sty.getValue();
		JSONArray arr;

		try {
			arr = new JSONArray(styles);
			for (int i = 0; i < arr.length(); i++) {
				list.add(arr.getString(i));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public static List<String> getSkillsToLearn(Map<String, String> allSkills) {
		List<String> SkillsToBuild = new ArrayList<>();
		Set<String> keys = allSkills.keySet();
		String cat = null;
		for (String s : keys) {
			cat = allSkills.get(s);
			if (cat.equalsIgnoreCase(SkillCategory.TO_BUILD.name())) {
				SkillsToBuild.add(s);
			}
		}
		return SkillsToBuild;
	}

	public static List<String> getLearntSkills(Map<String, String> allSkills) {
		List<String> learntSkills = new ArrayList<>();
		Set<String> keys = allSkills.keySet();
		String cat = null;
		for (String s : keys) {
			cat = allSkills.get(s);
			if (cat.equalsIgnoreCase(SkillCategory.HAVE_BUILT.name())) {
				learntSkills.add(s);
			}
		}
		return learntSkills;
	}

	private static Map<String, String> getAllSkills(String id) {
		Record rec = new Record(id, StringConstants.SKILL_BUILDER);
		RecordJpaController cont = new RecordJpaController();
		rec = cont.findRecord(rec.getKey());
		if (rec == null) {
			return Collections.emptyMap();
		}
		return rec.getSkills();
	}

	public static String getMits(String id) {
		Record rec = new Record(id, StringConstants.MULTIPLE_INTELLIGENCE_TEST);
		RecordJpaController cont = new RecordJpaController();
		rec = cont.findRecord(rec.getKey());
		if (rec == null) {
			return null;
		}
		Text types = rec.getMitTypes();
		return types.getValue();
	}

	public static List<String> getAllStrongMits(String value) {

		List<String> list = new ArrayList<>();
		if (value == null) {
			return list;
		} else {
			try {
				JSONArray arr = new JSONArray(value);

				String token = null;
				String val = null;
				int v = 0;
				int a = 0;
				int b = 0;
				int c = 0;
				for (int i = 0; i < arr.length(); i++) {
					token = arr.getString(i);
					a = token.indexOf(':') + 2;
					token = token.substring(a);

					b = token.indexOf(':') + 1;
					c = token.lastIndexOf('}');
					val = token.substring(b, c).trim();
					token = token.substring(0, token.indexOf('"'));
					v = Integer.parseInt(val);
					if (v > 10) {
						list.add(token);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return list;
		}

	}

	public static Map<String, String> getAllStrongMits(String value, int from, int to) {

		Map<String,String> map = new HashMap<>();
		if (value == null) {
			return map;
		} else {
			try {
				JSONArray arr = new JSONArray(value);

				String token = null;
				String val = null;
				int v = 0;
				int a = 0;
				int b = 0;
				int c = 0;
				for (int i = 0; i < arr.length(); i++) {
					token = arr.getString(i);
					a = token.indexOf(':') + 2;
					token = token.substring(a);

					b = token.indexOf(':') + 1;
					c = token.lastIndexOf('}');
					val = token.substring(b, c).trim();
					token = token.substring(0, token.indexOf('"'));
					v = Integer.parseInt(val);
					if (v >= from && v < to) {
						map.put(token,val);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map;
		}

	}

	public static List<String> getTalents(String id) {
		Record rec = new Record(id, StringConstants.TALENT_HUNT);
		RecordJpaController cont = new RecordJpaController();
		rec = cont.findRecord(rec.getKey());
		if (rec == null) {
			return new ArrayList<String>();
		}
		Map<String, String> map = rec.getTalents();
		return getStrongTalents(map);
	}
	
	public static Map<String,String> getTalents(String id, Boolean withValues) {
		Record rec = new Record(id, StringConstants.TALENT_HUNT);
		RecordJpaController cont = new RecordJpaController();
		rec = cont.findRecord(rec.getKey());
		if (rec == null) {
			return new HashMap<String,String>();
		}
		Map<String, String> map = rec.getTalents();
		return getStrongTalents(map, withValues);
	}

	public static List<String> getStrongTalents(Map<String, String> map) {
		List<String> talents = new ArrayList<>();
		Set<String> keys = map.keySet();
		int val = 0;
		for (String s : keys) {
			val = Integer.parseInt(map.get(s));
			if (val >= 6) {
				talents.add(s);
			}
		}
		return talents;
	}
	
	public static Map<String,String> getStrongTalents(Map<String, String> map, Boolean withValues) {
		Map<String,String> mp = new HashMap<>();
		Set<String> keys = map.keySet();
		for (String s : keys) {
			if (Integer.parseInt(map.get(s)) >= 6) {
				mp.put(s,map.get(s));
			}
		}
		return mp;
	}

	private static List<String> getClusters(String id) {
		Record rec = new Record(id, StringConstants.CAREER_CLUSTERS);
		RecordJpaController cont = new RecordJpaController();
		rec = cont.findRecord(rec.getKey());
		if (rec == null) {
			return new ArrayList<>();
		}
		Map<String, String> map = rec.getCareerClusters();
		return getLovedClusters(map);
	}

	public static List<String> getLovedClusters(Map<String, String> map) {

		List<String> clusters = new ArrayList<>();
		Set<String> keys = map.keySet();
		int val = 0;
		for (String s : keys) {
			val = Integer.parseInt(map.get(s));
			if (val > 12) {
				clusters.add(s);
			}
		}
		return clusters;
	}
	
	public static Map<String,String> getClusters(String id, boolean withValues) {
		Record rec = new Record(id, StringConstants.CAREER_CLUSTERS);
		RecordJpaController cont = new RecordJpaController();
		rec = cont.findRecord(rec.getKey());
		if (rec == null) {
			return new HashMap<String,String>();
		}
		Map<String, String> map = rec.getCareerClusters();
		return getLovedClusters(map, withValues);
	}

	public static Map<String,String> getLovedClusters(Map<String, String> map, boolean withValues) {

		Map<String,String> clusters = new HashMap<>();
		Set<String> keys = map.keySet();
		int val = 0;
		for (String s : keys) {
			val = Integer.parseInt(map.get(s));
			if (val > 12) {
				clusters.put(s,map.get(s));
			}
		}
		return clusters;
	}


	private static List<String> getValues(String id) {
		Record rec = new Record(id, StringConstants.CAREER_VALUES);

		RecordJpaController cont = new RecordJpaController();
		rec = cont.findRecord(rec.getKey());
		if (rec == null) {
			return new ArrayList<>();
		} else {

			return asValuesList(rec.getCareerValues());
		}

	}

	public static List<String> asValuesList(Text vals) {
		String values = vals.getValue();
		List<String> list = new ArrayList<>();
		try {
			JSONArray arr = new JSONArray(values);
			int a = 0;
			int b = 0;
			String value = null;
			for (int i = 0; i < arr.length(); i++) {
				value = arr.getString(i);
				a = value.lastIndexOf(":") + 2;
				b = value.lastIndexOf('"');
				value = value.substring(a, b);
				list.add(value);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static String encodeId(String s) {
		s = s.replaceAll("2", "a").replaceAll("3", "A").replaceAll("5", "b")
				.replaceAll("7", "B").replaceAll("0", "F");
		return s;
	}

	public static String decodeId(String s) {
		s = s.replaceAll("a", "2").replaceAll("A", "3").replaceAll("b", "5")
				.replaceAll("B", "7").replaceAll("F", "0");
		return s;
	}

	public static ModuleInfo getSixMore(ModuleInfo mi) {
		List<String> nextSix = new ArrayList<>();
		List<String> oList = mi.getList();
		int counter = mi.getCounter();
		while (nextSix.size() < 6) {
			if (counter >= oList.size()) {
				counter = 0;
			}
			nextSix.add(oList.get(counter));
			counter++;
		}
		mi.setCounter(counter);
		mi.setList(nextSix);
		return mi;
	}
}
