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
import com.jevalab.azure.profile.UserProfile;

public class ProfileHelper {

	public static AzureUser getProfileUser(String id) {
		UserJpaController cont = new UserJpaController();
		AzureUser user = cont.findUser(id);
		return user;
	}

	public static UserProfile getProfileData(AzureUser user, UserProfile profile) {


		profile.setFirstName(user.getFirstName());
		profile.setLastName(user.getLastName());
		profile.setMiddleName(user.getMiddleName());
		profile.setGender(user.getGender());
		profile.setEmail(user.getEmail());
		profile.setSchool(user.getSchool());
		profile.setState(user.getState());
		profile.setCountry(user.getCountry());
		profile.setCover(user.getCover());
		profile.setPicture(user.getPicture());
		profile.setLastSeenDate(user.getLastSeenDate().toString());
		profile.setAbout((user.getAbout() == null) ? null : user.getAbout()
				.getValue());
		profile.setBirthDate(user.getDateOfBirth());
		profile.setCollections((user.getCollections()==null)?0:user.getCollections().size());
		profile.setCommunities((user.getCommunities()==null)?0:user.getCommunities().size());
		profile.setFollowers((user.getFollowers()==null)?0:user.getFollowers().size());
		profile.setFollowing((user.getFollowing()==null)?0:user.getFollowing().size());
		profile.setGrade(user.getsClass());
		profile.setHobbies((user.getHobbies()==null)?null:user.getHobbies().getValue());
		List<String> interest = Util.toInterestValues(user.getAreaOfInterest());
		String is = "";
		for(String s: interest) {
			is+=(s+", ");
		}
		is=is.substring(0,is.lastIndexOf(","));
		profile.setInterest(is);
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

	public static String getSkillsToLearn(Map<String, String> allSkills) {
		String SkillsToBuild = "";
		Set<String> keys = allSkills.keySet();
		String cat = null;
		for (String s : keys) {
			cat = allSkills.get(s);
			if (cat.equalsIgnoreCase(SkillCategory.TO_BUILD.name())) {
				SkillsToBuild += (s + ", ");
			}
		}
		
		if(SkillsToBuild.isEmpty()) {
			return SkillsToBuild;
		}else {
			return SkillsToBuild.substring(0,SkillsToBuild.lastIndexOf(","));
		}
	}

	public static String getLearntSkills(Map<String, String> allSkills) {
		String learntSkills = "";
		Set<String> keys = allSkills.keySet();
		String cat = null;
		for (String s : keys) {
			cat = allSkills.get(s);
			if (cat.equalsIgnoreCase(SkillCategory.HAVE_BUILT.name())) {
				learntSkills += (s + ", ");
			}
		}
		if(learntSkills.isEmpty()) {
			return learntSkills;
		}else {
			return learntSkills.substring(0,learntSkills.lastIndexOf(","));
		}
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

	public static String getAllStrongMits(String value) {

		String list = "";
		if (value == null) {
			return null;
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
						list += (token + ", ");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(list.isEmpty()) {
				return list;
			}else {
				return list.substring(0,list.lastIndexOf(","));
			}
		}

	}

	public static Map<String, String> getAllStrongMits(String value, int from,
			int to) {

		Map<String, String> map = new HashMap<>();
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
						map.put(token, val);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map;
		}

	}

	public static String getTalents(String id) {
		Record rec = new Record(id, StringConstants.TALENT_HUNT);
		RecordJpaController cont = new RecordJpaController();
		rec = cont.findRecord(rec.getKey());
		if (rec == null) {
			return null;
		}
		Map<String, String> map = rec.getTalents();
		return getStrongTalents(map);
	}

	public static Map<String, String> getTalents(String id, Boolean withValues) {
		Record rec = new Record(id, StringConstants.TALENT_HUNT);
		RecordJpaController cont = new RecordJpaController();
		rec = cont.findRecord(rec.getKey());
		if (rec == null) {
			return new HashMap<String, String>();
		}
		Map<String, String> map = rec.getTalents();
		return getStrongTalents(map, withValues);
	}

	public static String getStrongTalents(Map<String, String> map) {
		String talents = "";
		Set<String> keys = map.keySet();
		int val = 0;
		for (String s : keys) {
			val = Integer.parseInt(map.get(s));
			if (val >= 6) {
				talents += (s + ", ");
			}
		}
		if(talents.isEmpty()) {
			return talents;
		}else {
			
			return talents.substring(0,talents.lastIndexOf(","));
		}
	}

	public static Map<String, String> getStrongTalents(Map<String, String> map,
			Boolean withValues) {
		Map<String, String> mp = new HashMap<>();
		Set<String> keys = map.keySet();
		for (String s : keys) {
			if (Integer.parseInt(map.get(s)) >= 6) {
				mp.put(s, map.get(s));
			}
		}
		return mp;
	}

	private static String getClusters(String id) {
		Record rec = new Record(id, StringConstants.CAREER_CLUSTERS);
		RecordJpaController cont = new RecordJpaController();
		rec = cont.findRecord(rec.getKey());
		if (rec == null) {
			return null;
		}
		Map<String, String> map = rec.getCareerClusters();
		return getLovedClusters(map);
	}

	public static String getLovedClusters(Map<String, String> map) {

		String clusters = "";
		Set<String> keys = map.keySet();
		int val = 0;
		for (String s : keys) {
			val = Integer.parseInt(map.get(s));
			if (val > 12) {
				clusters += (s + ", ");
			}
		}

		if(clusters.isEmpty()) {
			return clusters;
		}else {
			return clusters.substring(0,clusters.lastIndexOf(","));
		}
	}

	public static Map<String, String> getClusters(String id, boolean withValues) {
		Record rec = new Record(id, StringConstants.CAREER_CLUSTERS);
		RecordJpaController cont = new RecordJpaController();
		rec = cont.findRecord(rec.getKey());
		if (rec == null) {
			return new HashMap<String, String>();
		}
		Map<String, String> map = rec.getCareerClusters();
		return getLovedClusters(map, withValues);
	}

	public static Map<String, String> getLovedClusters(Map<String, String> map,
			boolean withValues) {

		Map<String, String> clusters = new HashMap<>();
		Set<String> keys = map.keySet();
		int val = 0;
		for (String s : keys) {
			val = Integer.parseInt(map.get(s));
			if (val > 12) {
				clusters.put(s, map.get(s));
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

}
