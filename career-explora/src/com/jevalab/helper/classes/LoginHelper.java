package com.jevalab.helper.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jevalab.azure.ProcessTestTopic;
import com.jevalab.azure.ProcessedUpComingTest;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.IdSequence;
import com.jevalab.azure.persistence.IdSequenceJpaController;
import com.jevalab.azure.persistence.PasswordRecoveryJpaController;
import com.jevalab.azure.persistence.Record;
import com.jevalab.azure.persistence.RecordJpaController;
import com.jevalab.azure.persistence.UpComingTest;
import com.jevalab.azure.persistence.UpComingTestTopic;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.PreexistingEntityException;
import com.jevalab.exceptions.RollbackFailureException;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;
import com.sun.swing.internal.plaf.synth.resources.synth;

public class LoginHelper {

	public static void requestDispatcher(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		String uri = req.getRequestURL().toString();
		int a = uri.lastIndexOf('/');
		uri = uri.substring(a);
		System.out.println(uri);
		RequestDispatcher rd = req.getRequestDispatcher(uri);
		rd.forward(req, res);
	}

	public static AzureUser initNewAzureUser(AzureUser user, User me) {

		user.setFirstName(me.getFirstName());
		user.setLastName(me.getLastName());
		
		user.setGender(me.getGender());
		
		if(me.getMiddleName()!=null) {
			user.setMiddleName(me.getMiddleName());
		}
		
		if(me.getEmail()!=null) {
			user.setEmail(me.getEmail());
		}
		
		user.setUpdateNameFromIdp(true);
		user.setUpComingTests(new ArrayList<UpComingTest>());
		user.setLastSeenDate(null);
		user.setLastTestTaken(null);
		user.setFriendsId(new ArrayList<String>());
		user.setOldPasswords(new ArrayList<String>());
		return user;
	}

	public static User getFacebookDetails(String accessToken, String appSecret,
			Version version) throws FacebookOAuthException {

		FacebookClient fbClient = new DefaultFacebookClient(accessToken,
				appSecret, version);

		User me = fbClient.fetchObject("me", User.class);
		return me;
	}

	public static AzureUser editExistingUser(User me, AzureUser user) {
		if (me != null) {

			if (user.isUpdateNameFromIdp()) {

				if (me.getFirstName() != null) {

					user.setFirstName(me.getFirstName());
				}

				if (me.getLastName() != null) {

					user.setLastName(me.getLastName());
				}

				if (me.getMiddleName() != null) {

					user.setMiddleName(me.getMiddleName());

				}
			}

			if (me.getGender() != null) {

				user.setGender(me.getGender());

			}

			if (me.getEmail() != null) {
				user.setEmail(me.getEmail());

			}

		}

		return user;
	}

	public static WelcomePageBean initWelcomePageBean(AzureUser user) {
		WelcomePageBean wpb = new WelcomePageBean();
		wpb.setBackgroundImg(user.getCover());
		wpb.setFirstName(user.getFirstName());
		wpb.setLastName(user.getLastName());
		Map<String, Object> map = Util.getPreferredPosts(user,0);
		wpb.setOffset((Integer) map.get("offset"));
		wpb.setPosts((List<DiscussionBean>) map.get("post"));
		wpb.setProfileImg(user.getPicture());
		wpb.setSchool(user.getSchool());
		wpb.setsClass(Util.getClassValue(user.getsClass()));
		return wpb;
	}

	public static Map<String, String> getLastTestData(String recordID) {
		Key key = KeyFactory.stringToKey(recordID);
		RecordJpaController cont = new RecordJpaController();
		Record record = cont.findRecord(key);
		Map<String, String> map = new HashMap<>();
		if (record != null) {
			map.put(StringConstants.TEST_DATE, record.getTestDate());
			map.put(StringConstants.TEST_NAME, record.getTestName());
		}
		return map;
	}

	public static Calendar getDateObject(String date) {
		if (date.contains("/")) {
			String[] vars = date.split("/");
			int mm = Integer.parseInt(vars[0]);
			mm = mm - 1;
			int dd = Integer.parseInt(vars[1]);
			int yy = Integer.parseInt(vars[2]);

			Calendar c = new GregorianCalendar(yy, mm, dd);

			return c;
		} else {
			return null;
		}

	}

	public static AzureUser persistUser(AzureUser user, PasswordRecovery pr) {

		Calendar millis = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

		Date d = millis.getTime();

		user.setLastSeenDate(d);

		UserJpaController cont = new UserJpaController();

		try {
			if (user.isNewUser()) {
				user.setNewUser(false);
				user = cont.create(user, pr);
				
			} else {

				cont.create(user, null);
			}
		} catch (Exception e) {
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public static List<UserView> getSuggestedFriends(AzureUser me,
			HttpSession session) {

		final int STEP = 3;
		UserJpaController cont = new UserJpaController();
		Object o = null;
		synchronized (session) {
			o = session.getAttribute(StringConstants.SF_COUNTER);
		}

		Integer sfCounter = 0;
		if (o != null && o instanceof Integer) {
			sfCounter = (Integer) o;
		} else {
			sfCounter = 0;
		}

		List<AzureUser> users = getUsers(sfCounter, STEP, cont);

		

		users = removeMe(me, users);

		users = getNonFriends(me, users);

		Map<String, Object> map = completeList(me, users, STEP, sfCounter, cont);
		if (map == null) {
			return null;
		} else {
			users = (List<AzureUser>) map.get(StringConstants.USERS);
			sfCounter = (Integer) map.get(StringConstants.COUNTER);

			List<UserView> list = toUserView(users);

			session.setAttribute(StringConstants.SF_COUNTER, sfCounter);
			return list;
		}

	}

	private static List<UserView> toUserView(List<AzureUser> users) {
		List<UserView> list = new ArrayList<>();

		for (AzureUser u : users) {
			UserView uv = new UserView();
			uv.setId(u.getUserID());
			uv.setFirstName(u.getFirstName());
			uv.setMiddlename(u.getMiddleName());
			uv.setLastName(u.getLastName());
			uv.setPicture(u.getPicture());
			uv.setCover(u.getCover());
			list.add(uv);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private static List<AzureUser> getUsers(int start, int max,
			UserJpaController cont) {

		List<AzureUser> users = cont.findUserBySchool(null, start, max);
		users = changeResultSet(users);
		return users;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List changeResultSet(List list) {
		List l = new ArrayList();
		l.addAll(list);
		return l;
	}

	private static List<AzureUser> removeMe(AzureUser me, List<AzureUser> users) {
		int i = 0;
		for (AzureUser a : users) {
			if (a.getUserID().equalsIgnoreCase(me.getUserID())) {
				users.remove(i);
				break;
			} else {
				i++;
			}
		}
		return users;
	}

	private static List<AzureUser> getNonFriends(AzureUser me,
			List<AzureUser> users) {
		List<AzureUser> nonFriends = new ArrayList<>();
		List<String> friends = me.getFriendsId();
		if (friends == null) {
			return users;
		} else if (friends.isEmpty()) {
			return users;
		} else {
			for (int i = 0; i < users.size(); i++) {
				for (int j = 0; j < friends.size(); j++) {
					if (users.get(i).getUserID()
							.equalsIgnoreCase(friends.get(j))) {
						continue;
					} else {
						nonFriends.add(users.get(i));
					}
				}
			}
			return nonFriends;
		}

	}

	private static Map<String, Object> completeList(AzureUser me,
			List<AzureUser> users, final int STEP, Integer sfCounter,
			UserJpaController cont) {

		Map<String, Object> map = null;
		
		if (users.size() >= 3) {
			map = new HashMap<>();
			map.put(StringConstants.USERS, users);
			map.put(StringConstants.COUNTER, sfCounter+=STEP);
			return map;
		} else {
			int totalNumber = cont.getUserCount();
			int diff = totalNumber - sfCounter;
			int deficit = STEP - users.size();
			List<AzureUser> extra = null;
			if(diff > deficit) {
				extra = getUsers(sfCounter, deficit, cont);
				sfCounter += extra.size();
			} else {
				sfCounter = 0;
				extra = getUsers(sfCounter, deficit, cont);
				sfCounter += deficit;
			}
			
			int completeSize = extra.size();
			extra = removeMe(me, extra);
			extra = getNonFriends(me, extra);
			int newSize = extra.size();
			
			if(completeSize > newSize) {
				users.addAll(extra);
				completeList(me, users, STEP, sfCounter, cont);
			} else {
				users.addAll(extra);
				map = new HashMap<>();
				map.put(StringConstants.USERS, users);
				map.put(StringConstants.COUNTER, sfCounter);
				return map;
			}
		}

		map = new HashMap<>();
		map.put(StringConstants.USERS, users);
		map.put(StringConstants.COUNTER, sfCounter);

		return map;
	}

	public static List<ProcessedUpComingTest> getUpcomingTest(
			List<UpComingTest> upComingTests) {

		if (upComingTests != null) {
			List<ProcessedUpComingTest> list = new ArrayList<>();
			ProcessedUpComingTest puct = null;

			for (UpComingTest c : upComingTests) {

				puct = new ProcessedUpComingTest(c);
				list.add(puct);
			}
			return list;
		} else {
			return Collections.emptyList();
		}
	}

	public static List<ProcessTestTopic> asProcessedTopics(
			List<UpComingTestTopic> tops) {
		if (tops != null) {
			ProcessTestTopic tTopic = null;
			List<ProcessTestTopic> topics = new ArrayList<>();
			for (UpComingTestTopic tt : tops) {
				tTopic = new ProcessTestTopic();
				tTopic.setCovered(tt.isCovered());
				tTopic.setTopicName(tt.getTopicName());
				topics.add(tTopic);
			}
			return topics;

		} else {
			return Collections.emptyList();
		}

	}

	public static AzureUser setTakenTalentTest(AzureUser user) {
		List<String> talents = ProfileHelper.getTalents(user.getUserID());
		if (talents == null || talents.isEmpty()) {
			user.setTakenTalentTest(false);
		} else {
			user.setTakenTalentTest(true);
		}
		return user;
	}

	public static List<UpComingTestTopic> asUpComingTestTopicsList(String tps) {

		String[] topics = null;
		UpComingTestTopic uctt = null;
		List<UpComingTestTopic> lists = new ArrayList<>();

		if (tps != null) {
			if (tps.contains(",")) {
				topics = tps.split(",");
				for (String s : topics) {
					uctt = new UpComingTestTopic(s, false);
					lists.add(uctt);
				}
			} else {
				uctt = new UpComingTestTopic(tps, false);
				lists.add(uctt);
			}
		}
		return lists;
	}

	public static void redirectUser(AzureUser user, HttpServletResponse res,
			HttpServletRequest req) throws ServletException, IOException {
		if (user.isFromAuthorization() || user.isAuthorized()) {
			LoginHelper.requestDispatcher(req, res);
		} else {

			req.getRequestDispatcher("/authorization").forward(req, res);
		}
	}

	public synchronized static String getNextId() {
		IdSequenceJpaController cont = new IdSequenceJpaController();
		
		IdSequence seq = (IdSequence)cont.getSequence(1l);
		long val = seq.getValue();
		try {
			cont.destroy(1l);
			val += 1;
			IdSequence sq = new IdSequence();
			sq.setValue(val);
			cont.create(sq);
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
		String randomString = Util.generateRandomCode(100000, 900000);
		return randomString+"@"+seq.getValue();
	}

	private static String getDateString(Calendar cd) {
		int x = cd.get(Calendar.DAY_OF_MONTH);
		String xs = "";
		if (x < 10) {
			xs = "0" + x;
		} else {
			xs += x;
		}
		x = cd.get(Calendar.MONTH);
		if (x < 10) {
			xs = xs + "0" + x;
		} else {
			xs += x;
		}

		x = cd.get(Calendar.YEAR);
		xs += x;
		return xs;
	}

	public static void checkIdSequence() {
		IdSequenceJpaController cont = new IdSequenceJpaController();
		IdSequence id = (IdSequence) cont.getSequence(1l);
		if (id == null) {
			IdSequence seq = new IdSequence();
			seq.setValue(1000000l);
			try {
				cont.create(seq);
			} catch (RollbackFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Entity createEntityFromUser(AzureUser user) {
		Entity e = new Entity("AzureUser", user.getUserID());
		e.setProperty(StringConstants.cFirstName, user.getFirstName());
		e.setProperty(StringConstants.cLastName, user.getLastName());
		e.setProperty(StringConstants.cMiddleName, user.getMiddleName());
		e.setUnindexedProperty(StringConstants.cGender, user.getGender());
		e.setProperty(StringConstants.cEMail, user.getEmail());
		e.setProperty(StringConstants.cState, user.getState());
		e.setProperty(StringConstants.cCountry, user.getCountry());
		e.setProperty(StringConstants.cSchool, user.getSchool());
		e.setUnindexedProperty(StringConstants.cUpdateName,
				user.isUpdateNameFromIdp());
		e.setUnindexedProperty(StringConstants.cLastTestTaken,
				user.getLastTestTaken());
		e.setUnindexedProperty(StringConstants.cDateOfBirth,
				user.getDateOfBirth());
		e.setUnindexedProperty(StringConstants.cLastSeenDate,
				user.getLastSeenDate());
		e.setUnindexedProperty(StringConstants.cAuthorized, user.isAuthorized());
		e.setUnindexedProperty(StringConstants.cUpComingTests,
				user.getUpComingTests());
		e.setUnindexedProperty(StringConstants.cSubscriptionDate,
				user.getSubscriptionDate());
		e.setUnindexedProperty(StringConstants.cValidity, user.getValidity());
		e.setUnindexedProperty(StringConstants.cAttends, user.isAttends());
		e.setUnindexedProperty(StringConstants.cPicture, user.getPicture());
		e.setUnindexedProperty(StringConstants.cCover, user.getCover());
		e.setUnindexedProperty(StringConstants.cFriends, user.getFriendsId());
		e.setProperty(StringConstants.cUsername, user.getUsername());
		e.setProperty(StringConstants.cMobile, user.getMobile());
		e.setUnindexedProperty(StringConstants.cPassword, user.getPassword());
		e.setProperty(StringConstants.cPasswordRecoveryIds,
				user.getPasswordRecoveryIds());
		e.setUnindexedProperty(StringConstants.cLastPasswordChangeDate, user.getLastPasswordChangeDate());
		e.setUnindexedProperty(StringConstants.cOldPasswords, user.getOldPasswords());
		e.setUnindexedProperty(StringConstants.cUserPicturesIds,user.getUserPicturesIds() );
		e.setUnindexedProperty(StringConstants.cFreeAccess, user.isFreeAccess());
		e.setIndexedProperty("Class", user.getsClass());
		e.setIndexedProperty("AreaOfInterest", user.getAreaOfInterest());
		return e;
	}

	public static Entity createEntityFromPasswordRecovery(PasswordRecovery pr) {
		Entity e = new Entity("PasswordRecovery", pr.getKey().getName());
		e.setUnindexedProperty("email", pr.isEmail());
		e.setUnindexedProperty("mobile", pr.isMobile());
		e.setUnindexedProperty("defaultRecovery", pr.isDefaultRecovery());
		e.setUnindexedProperty("verified", pr.isVerified());
		return e;
	}

	public static Entity createEntityFromUserPicture(UserPicture up) {
		Entity e = new Entity("UserPicture",up.getKey().getName());
		e.setUnindexedProperty("blobkey", up.getBlobkey());
		e.setUnindexedProperty("url", up.getUrl());
		e.setUnindexedProperty("isCurrentPicture", up.isCurrentPicture());
		e.setUnindexedProperty("type", up.getType());
		return e;
	}

}
