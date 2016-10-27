package com.jevalab.helper.classes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.gson.Gson;
import com.jevalab.azure.people.Friends;
import com.jevalab.azure.people.PeoplePageBean;
import com.jevalab.azure.people.Person;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.Collection;
import com.jevalab.azure.persistence.Community;
import com.jevalab.azure.persistence.Discussion;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.azure.persistence.MultipleIntelligenceTestQuestion;
import com.jevalab.azure.persistence.MultipleIntelligenceTestQuestionJpaController;
import com.jevalab.azure.persistence.PasswordRecoveryJpaController;
import com.jevalab.azure.persistence.Record;
import com.jevalab.azure.persistence.RecordJpaController;
import com.jevalab.azure.persistence.SkillCategory;
import com.jevalab.azure.persistence.Talent;
import com.jevalab.azure.persistence.TalentCategory;
import com.jevalab.azure.persistence.TalentCategoryJpaController;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.azure.profile.UserProfile;
import com.jevalab.exceptions.IpnException;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.RollbackFailureException;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;

public class Util {

	private final static Logger LOGGER = Logger.getLogger(Util.class.getName());
	private static final MemcacheService GROUPS = MemcacheServiceFactory
			.getMemcacheService("groups");
	static {
		LOGGER.setLevel(Level.FINEST);
	}

	public static boolean notNull(String... args) {
		if (args == null) {
			return false;
		}
		for (String s : args) {
			if (s == null || s.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public static Map<String, String> validateRegistrationForm(
			RegistrationForm form) {
		Map<String, String> resp = new HashMap<>();

		// check if username already exists
		boolean exists = usernameExists(form.getUsername());

		if (exists) {

			if (form.isUseEmail()) {
				resp.put(
						StringConstants.ERROR,
						"The email <span style='color:blue'> "
								+ form.getUsername()
								+ "</span> is already in use.");
			} else if (form.isUseMobile()) {
				resp.put(
						StringConstants.ERROR,
						"The mobile number <span style='color:blue'> "
								+ form.getUsername()
								+ " </span> is already in use.");
			} else {
				resp.put(
						StringConstants.ERROR,
						"The Username <span style='color:blue'> "
								+ form.getUsername()
								+ "</span> is already in use.");
			}

			return resp;
		}

		/******************************************/
		// validate first name

		boolean notValid = (form.getFirstName() == null);

		if (notValid) {
			resp.put(StringConstants.ERROR, "Please enter your first name.");
			return resp;
		}

		if (form.getFirstName().isEmpty()) {
			resp.put(StringConstants.ERROR, "Please enter your first name.");
			return resp;
		}

		if (!(isAlpha(form.getFirstName()))) {
			resp.put(StringConstants.ERROR,
					"Your first name should contain only alphabets.");
			return resp;
		}

		/*********************************************/
		// validate last name

		notValid = (form.getLastName() == null);

		if (notValid) {
			resp.put(StringConstants.ERROR, "Please enter your last name.");
			return resp;
		}

		if (form.getLastName().isEmpty()) {
			resp.put(StringConstants.ERROR, "Please enter your last name.");
			return resp;
		}

		if (!(isAlpha(form.getLastName()))) {
			resp.put(StringConstants.ERROR,
					"Your last name should contain only alphabets.");
			return resp;
		}

		/******************************************/
		// validate username
		notValid = (form.getUsername() == null);

		if (form.isUseMobile()) {
			if (notValid) {
				resp.put(StringConstants.ERROR,
						"Please enter your mobile number.");
				return resp;
			}

			if (form.getUsername().isEmpty()) {
				resp.put(StringConstants.ERROR,
						"Please enter your mobile number.");
				return resp;
			}

			if (form.getUsername().length() != 11) {
				resp.put(StringConstants.ERROR,
						"Please enter a valid mobile number.");
				return resp;
			}

			if (!(isNumeric(form.getUsername()))) {
				resp.put(StringConstants.ERROR,
						"Your mobile number cannot contain alphabets.");
				return resp;
			}
		} else if (form.isUseEmail()) {
			if (notValid) {
				resp.put(StringConstants.ERROR,
						"Please enter your email address.");
				return resp;
			}

			if (form.getUsername().isEmpty()) {
				resp.put(StringConstants.ERROR,
						"Please enter your email address.");
				return resp;
			}

			if (!isEmail(form.getUsername())) {
				resp.put(StringConstants.ERROR,
						"Please enter a valid email address.");
				return resp;
			}
		} else {
			if (notValid) {
				resp.put(StringConstants.ERROR, "Please enter your username.");
				return resp;
			}

			if (form.getUsername().isEmpty()) {
				resp.put(StringConstants.ERROR, "Please enter your username.");
				return resp;
			}
		}

		/****************************************************/
		// validate password1

		notValid = (form.getPassword1() == null);
		if (notValid) {
			resp.put(StringConstants.ERROR, "Please enter a password.");
			return resp;
		}

		if (form.getPassword1().isEmpty()) {
			resp.put(StringConstants.ERROR, "Please enter a password.");
			return resp;
		}

		if (form.getPassword1().length() < 7
				| form.getPassword1().length() > 21) {
			resp.put(StringConstants.ERROR,
					"Your password should have between 7 to 21 characters.");
			return resp;
		}

		if (!(isValidpassword(form.getPassword1()))) {
			resp.put(StringConstants.ERROR,
					"Your password should contain at least a digit and a special character.");
			return resp;
		}

		/*************************************************/
		// validate password2
		notValid = (form.getPassword2() == null);
		if (notValid) {
			resp.put(StringConstants.ERROR, "Please re-enter your password.");
			return resp;
		}

		if (form.getPassword2().isEmpty()) {
			resp.put(StringConstants.ERROR, "Please re-enter your password.");
			return resp;
		}

		/*********************************************/
		// passwords match
		if (!(form.getPassword1().equals(form.getPassword2()))) {
			resp.put(StringConstants.ERROR, "Passwords do not match.");
			return resp;
		}

		/***********************************************/
		// validate Gender
		notValid = (form.getGender() == null);
		if (notValid) {
			resp.put(StringConstants.ERROR, "Please select your gender.");
			return resp;
		}

		if (form.getGender().isEmpty()) {
			resp.put(StringConstants.ERROR, "Please select your gender.");
			return resp;
		}

		/*********************************************/
		// validate passwordRecoveryNumber
		if (!form.isUseEmail() && !form.isUseMobile()) {
			notValid = (form.getPasswordRecovery().getKey().getName() == null);

			if (notValid) {
				resp.put(StringConstants.ERROR,
						"Please enter a password recovery number.");
				return resp;
			}

			if (!(isNumeric(form.getPasswordRecovery().getKey().getName()))) {
				resp.put(StringConstants.ERROR,
						"Please enter a valid mobile number for password recovery.");
				return resp;
			}
		}

		resp.put("OK", "ok");
		return resp;
	}

	public static boolean isValidpassword(String str) {
		boolean x = str
				.matches("((?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{7,21})");
		return x;
	}

	public static boolean isEmail(String str) {
		boolean x = str.matches("^([\\S])+@([\\S])+\\.([\\S])+");
		return x;
	}

	public static boolean isNumeric(String str) {
		boolean x = str.matches("[0-9]+");
		return x;
	}

	private static boolean isAlpha(String str) {
		boolean x = str.matches("[a-zA-Z]+");
		return x;
	}

	private static boolean usernameExists(String username) {
		UserJpaController cont = new UserJpaController();
		AzureUser user = cont.findUserByUsername(username, true);
		if (user == null) {
			return false;
		} else {
			return true;
		}

	}

	public static void sendEmail(String to, String title, String body)
			throws AddressException, MessagingException {

		String from = "jaidadelahuya@gmail.com";
		Properties prop = System.getProperties();
		Session session = Session.getDefaultInstance(prop, null);

		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setSubject(title);
		msg.setText(body);
		// msg.setContent(body, "text/html");
		Transport.send(msg);

	}

	public static void sendSMS(String accSID, String authToken,
			RegistrationForm rf) throws TwilioRestException {
		TwilioRestClient client = new TwilioRestClient(accSID, authToken);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Body",
				StringConstants.CONFIRMATION_EMAIL_BODY
						+ rf.getConfirmationCode()));
		params.add(new BasicNameValuePair("To", "+2347051212230"));
		params.add(new BasicNameValuePair("From", "+13098224750"));

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		com.twilio.sdk.resource.instance.Message message = null;

		message = messageFactory.create(params);

		System.out.println(message.getSid());
	}

	public static String toJsonString(Object arg) {

		String json = null;
		json = new Gson().toJson(arg).toString();
		return json;
	}

	public static boolean updateLastTestTaken(HttpSession session, String data,
			final String TEST_NAME, String testDate, String subjectName,
			final String VENDOR) {

		Object o = null;
		Object o1 = null;
		synchronized (session) {
			o = session.getAttribute(StringConstants.WELCOME_PAGE);
			o1 = session.getAttribute(StringConstants.AZURE_USER);
		}

		if (o != null && o1 != null) {
			WelcomePageBean wpb = (WelcomePageBean) o;
			AzureUser user = (AzureUser) o1;
			Record record = new Record(user.getUserID(), TEST_NAME, testDate,
					subjectName);
			record.setCbtData(new Text(data));
			record.setVendor(VENDOR);

			user.setLastTestTaken(KeyFactory.keyToString(record.getKey()));
			RecordJpaController cont = new RecordJpaController();

			synchronized (session) {
				session.setAttribute(StringConstants.AZURE_USER, user);
				session.setAttribute(StringConstants.WELCOME_PAGE, wpb);
			}

			try {
				cont.create(record);
			} catch (RollbackFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;

		} else {
			return false;
		}
	}

	private static List<String> asList(Set<String> ts) {
		List<String> list = new ArrayList<>(ts);
		return list;
	}

	private static Set<String> asSet(List<String> list) {
		TreeSet<String> set = new TreeSet<>(list);
		return set;
	}

	private static void saveNewRecord(Record record, RecordJpaController cont) {
		try {
			cont.create(record);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void updateNewRecord(Record record, RecordJpaController cont) {
		try {
			cont.edit(record);
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void saveInSession(HttpSession session, Object object,
			String sessionName) {
		synchronized (session) {
			session.setAttribute(sessionName, object);
		}

	}

	private static boolean updateUserRecord(AzureUser user, Record record) {
		if (user == null) {
			return false;
		} else {
			user.setLastTestTaken(KeyFactory.keyToString(record.getKey()));
			return true;
		}

	}

	public static boolean updateWelcomePageBeanRecord(WelcomePageBean wpb,
			Record record) {
		if (wpb == null) {
			return false;
		} else {

			return true;
		}
	}

	public static UserProfile getUserProfileFromSession(HttpSession session) {
		Object o;
		synchronized (session) {
			o = session.getAttribute(StringConstants.USER_PROFILE);
		}

		UserProfile userProfile = null;
		if (o != null) {
			userProfile = (UserProfile) o;
		}
		return userProfile;
	}

	public static AzureUser getUserFromSession(HttpSession session) {
		Object o;
		synchronized (session) {
			o = session.getAttribute(StringConstants.AZURE_USER);
		}

		AzureUser user = null;
		if (o != null) {
			user = (AzureUser) o;
		}
		return user;
	}

	public static WelcomePageBean getWelcomePageBeanFromSession(
			HttpSession session) {
		Object o;
		synchronized (session) {
			o = session.getAttribute(StringConstants.WELCOME_PAGE);
		}

		WelcomePageBean wpb = null;
		if (o != null) {
			wpb = (WelcomePageBean) o;
		}
		return wpb;
	}

	public static Map<String, String> getTalentMap(List<String> vals) {
		String key = null;
		String val = null;

		Map<String, String> map = new TreeMap<>();

		int ks = 0;
		int vs = 0;
		int ke = 0;
		for (String s : vals) {

			vs = s.indexOf(':') + 1;
			val = s.substring(vs);
			ks = val.indexOf(':') + 2;
			ke = val.length() - 2;
			key = val.substring(ks, ke);
			val = val.substring(0, val.indexOf(','));

			map.put(key, val);
		}
		return map;
	}

	public static Map<String, String> getCareerMap(List<String> vals) {
		String key = null;
		String val = null;
		Map<String, String> map = new TreeMap<>();

		int ks = 0;
		int vs = 0;
		for (String s : vals) {
			ks = s.indexOf(':') + 2;
			key = s.substring(ks);
			vs = key.indexOf(':') + 1;
			val = key.substring(vs, key.length() - 1);
			key = key.substring(0, key.indexOf('"'));
			map.put(key, val);
		}
		return map;
	}

	public static List<String> asList(String items) {

		if (items == null) {
			return Collections.emptyList();
		} else {
			JSONArray arr;
			List<String> tals = new ArrayList<>();
			try {
				arr = new JSONArray(items);
				for (int i = 0; i < arr.length(); i++) {
					tals.add(arr.getString(i));
					System.out.println(arr.getString(i));
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return tals;
		}
	}

	public static Map<String, String> addToSkillRecords(List<String> b,
			SkillCategory category, Map<String, String> allSkillRecords) {

		String name = null;
		for (String s : b) {

			int a = s.indexOf(':') + 2;
			name = s.substring(a);
			name = name.substring(0, name.indexOf('"'));

			allSkillRecords.put(name, category.toString());
		}
		return allSkillRecords;
	}

	public static void useJSON(Object o, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");

		String json = null;
		json = new Gson().toJson(o).toString();
		System.out.println(json);
		resp.getWriter().write(json);
	}

	public static AzureUser authenticateUsername(String username) {
		UserJpaController cont = new UserJpaController();
		AzureUser user = cont.findUserByUsername(username, false);
		return user;
	}

	public static List<PasswordRecovery> getPasswordRecoveryListFromUser(
			AzureUser user, UserJpaController c1, String username,
			boolean verifiedOnly) {

		user = c1.findUserByUsername(username, false);
		Set<String> ps = user.getPasswordRecoveryIds();
		List<PasswordRecovery> list = new ArrayList<>();
		PasswordRecoveryJpaController c2 = new PasswordRecoveryJpaController();
		Iterator<String> iterator = ps.iterator();
		while (iterator.hasNext()) {
			String id = iterator.next();
			Key key = KeyFactory.createKey(
					PasswordRecovery.class.getSimpleName(), id);
			PasswordRecovery pr = c2.findPasswordRecovery(key);
			if (verifiedOnly) {
				if (pr.isVerified()) {
					list.add(pr);
				}
			} else {
				list.add(pr);
			}

		}
		return list;
	}

	public static PasswordRecovery getDefaultPasswordRecovery(
			List<PasswordRecovery> list) {
		PasswordRecovery dpr = null;
		for (PasswordRecovery pr : list) {
			if (pr.isDefaultRecovery()) {
				dpr = pr;
			}
		}
		return dpr;
	}

	public static boolean sendConfirmationToDefaultPasswordRecovery(
			PasswordRecovery dpr, RegistrationForm rf) {

		if (dpr.isVerified()) {

			if (dpr.isEmail()) {
				try {
					sendEmail(
							dpr.getKey().getName(),
							StringConstants.CONFIRMATION_EMAIL_SUBJECT,
							StringConstants.CONFIRMATION_EMAIL_BODY
									+ rf.getConfirmationCode());
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (dpr.isMobile()) {
				try {
					sendSMS(StringConstants.TWILIO_SID,
							StringConstants.TWILIO_AUTH_TOKEN, rf);
				} catch (TwilioRestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return true;
		} else {
			return false;
		}
	}

	public static String generateRandomCode(int minVal, int maxVal) {
		Random ran = new Random();
		return new Integer(minVal + ran.nextInt(maxVal)).toString();
	}

	public static boolean isFacebookID(String userID) {
		if (userID.contains("@"))
			return false;
		else
			return false;
	}

	public static PasswordRecovery getMobilePasswordRecovery(
			Set<String> passwordRecoveryIds) {
		PasswordRecoveryJpaController c1 = new PasswordRecoveryJpaController();
		PasswordRecovery pr = null;
		for (String id : passwordRecoveryIds) {
			Key key = KeyFactory.createKey(
					PasswordRecovery.class.getSimpleName(), id);
			pr = c1.findPasswordRecovery(key);
			if (pr.isMobile() && pr.isDefaultRecovery()) {
				break;
			} else {
				pr = null;
			}
		}
		return pr;
	}

	public static PasswordRecovery getAltMobilePasswordRecovery(
			Set<String> passwordRecoveryIds) {
		PasswordRecoveryJpaController c1 = new PasswordRecoveryJpaController();
		PasswordRecovery pr = null;
		for (String id : passwordRecoveryIds) {
			Key key = KeyFactory.createKey(
					PasswordRecovery.class.getSimpleName(), id);
			pr = c1.findPasswordRecovery(key);
			if (pr.isMobile() && !pr.isDefaultRecovery()) {
				break;
			} else {
				pr = null;
			}
		}
		return pr;
	}

	public static PasswordRecovery getEmailPasswordRecovery(
			Set<String> passwordRecoveryIds) {
		PasswordRecoveryJpaController c1 = new PasswordRecoveryJpaController();
		PasswordRecovery pr = null;
		for (String id : passwordRecoveryIds) {
			Key key = KeyFactory.createKey(
					PasswordRecovery.class.getSimpleName(), id);
			pr = c1.findPasswordRecovery(key);
			if (pr.isEmail() && pr.isDefaultRecovery()) {
				break;
			} else {
				pr = null;
			}
		}
		return pr;
	}

	public static PasswordRecovery getAltEmailPasswordRecovery(
			Set<String> passwordRecoveryIds) {
		PasswordRecoveryJpaController c1 = new PasswordRecoveryJpaController();
		PasswordRecovery pr = null;
		for (String id : passwordRecoveryIds) {
			Key key = KeyFactory.createKey(
					PasswordRecovery.class.getSimpleName(), id);
			pr = c1.findPasswordRecovery(key);
			if (pr.isEmail() && !pr.isDefaultRecovery()) {
				break;
			} else {
				pr = null;
			}
		}
		return pr;
	}

	public static Map<String, PasswordRecovery> oldAndNewPasswordRecovery(
			String oldId, String newId,
			List<PasswordRecovery> passwordRecoveries) {
		Map<String, PasswordRecovery> map = new HashMap<>();
		PasswordRecovery pr1 = new PasswordRecovery(oldId, false, false, false,
				false, false);
		PasswordRecovery pr2 = new PasswordRecovery(newId, false, false, false,
				false, false);
		for (PasswordRecovery p : passwordRecoveries) {
			if (pr1.equals(p)) {
				pr2.setDefaultRecovery(p.isDefaultRecovery());
				pr2.setEmail(p.isEmail());
				pr2.setMobile(p.isMobile());
				pr2.setUsername(p.isUsername());
				pr2.setVerified(p.isVerified());
				map.put("opr", p);
				map.put("npr", pr2);
				break;
			}
		}
		return map;
	}

	public static void sendSMS(String twilioSid, String twilioAuthToken,
			String code) throws TwilioRestException {
		TwilioRestClient client = new TwilioRestClient(twilioSid,
				twilioAuthToken);

		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Body",
				StringConstants.CONFIRMATION_EMAIL_BODY + code));
		params.add(new BasicNameValuePair("To", "+2347051212230"));
		params.add(new BasicNameValuePair("From", "+13098224750"));

		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		com.twilio.sdk.resource.instance.Message message = null;

		message = messageFactory.create(params);

		System.out.println(message.getSid());

	}

	public static boolean updateUserSettingsPasswordRecovery(
			PasswordRecovery pr, UserSettingsModel usm) {

		if (pr.isMobile()) {
			if (usm.isHasMobile()) {
				usm.setAltMobile(pr);
				return true;
			} else {
				usm.setMobile(pr);
				return true;
			}
		} else if (pr.isEmail()) {
			if (usm.isHasEmail()) {
				usm.setAltEmail(pr);
				return true;
			} else {
				usm.setEmail(pr);
				return true;
			}
		} else {
			return false;
		}

	}

	public static HttpServletResponse sendAjaxErrorMessage(String msg,
			HttpServletResponse resp) throws IOException {
		resp.resetBuffer();
		resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
		resp.setHeader("Content-Type", "text/plain");
		resp.getWriter().write(msg);
		resp.flushBuffer();
		return resp;
	}

	public static MitReport getMitReport(AzureUser user) {
		String mitsValues = ProfileHelper.getMits(user.getUserID());
		Map<String, String> vsmits = ProfileHelper.getAllStrongMits(mitsValues,
				16, 21);
		Map<String, String> smits = ProfileHelper.getAllStrongMits(mitsValues,
				13, 16);
		Map<String, String> fsmits = ProfileHelper.getAllStrongMits(mitsValues,
				10, 13);

		Map<MultipleIntelligenceTestQuestion, String> vs = new HashMap<>();
		Map<MultipleIntelligenceTestQuestion, String> s = new HashMap<>();
		Map<MultipleIntelligenceTestQuestion, String> fs = new HashMap<>();

		MultipleIntelligenceTestQuestionJpaController cont = new MultipleIntelligenceTestQuestionJpaController();
		Set<String> qs = vsmits.keySet();
		for (String q : qs) {
			MultipleIntelligenceTestQuestion mq = cont
					.findMultipleIntelligenceTestQuestion(q);
			vs.put(mq, vsmits.get(q));
		}
		Set<String> ps = smits.keySet();
		for (String q : ps) {
			MultipleIntelligenceTestQuestion mq = cont
					.findMultipleIntelligenceTestQuestion(q);
			s.put(mq, smits.get(q));
		}
		Set<String> rs = fsmits.keySet();
		for (String q : rs) {
			MultipleIntelligenceTestQuestion mq = cont
					.findMultipleIntelligenceTestQuestion(q);
			fs.put(mq, fsmits.get(q));
		}

		MitReport mr = new MitReport(s, vs, fs);
		return mr;
	}

	public static Map<String, String> getMitNames(
			Map<MultipleIntelligenceTestQuestion, String> oldMap) {

		Map<String, String> newMap = new HashMap<>();
		Set<MultipleIntelligenceTestQuestion> keys = oldMap.keySet();
		String mitName = null;
		for (MultipleIntelligenceTestQuestion mitq : keys) {
			mitName = mitq.getIntelligenceType();
			newMap.put(mitName, oldMap.get(mitq));
		}
		return newMap;
	}

	public static TalentReport getTalentReport(AzureUser user) {
		Map<String, String> map = ProfileHelper.getTalents(user.getUserID(),
				true);
		LOGGER.info(map.toString());
		List<TalentInfo> talentInfos = getTalentInfos(map);
		LOGGER.info(talentInfos.toString());
		TalentReport talentReport = initTalentReport(talentInfos);
		LOGGER.info(talentReport.toString());
		return talentReport;
	}

	private static TalentReport initTalentReport(List<TalentInfo> talentInfos) {
		Set<TalentInfo> headTalents = new HashSet<>();
		Set<TalentInfo> bodyTalents = new HashSet<>();
		Set<TalentInfo> handTalents = new HashSet<>();

		for (TalentInfo ti : talentInfos) {
			LOGGER.info(ti.toString());
			if (ti.getCategory().equalsIgnoreCase("head")) {
				headTalents.add(ti);
			} else if (ti.getCategory().equalsIgnoreCase("hand")) {
				handTalents.add(ti);
			} else if (ti.getCategory().equalsIgnoreCase("body")) {
				bodyTalents.add(ti);
			}
		}

		TalentReport report = new TalentReport(headTalents, bodyTalents,
				handTalents);
		return report;
	}

	private static List<TalentInfo> getTalentInfos(Map<String, String> map) {
		TalentInfo talentInfo = null;
		List<TalentInfo> talentInfos = new ArrayList<>();
		Set<String> keys = map.keySet();
		TalentCategoryJpaController cont = new TalentCategoryJpaController();
		List<TalentCategory> tcs = cont.findTalentCategoryEntities();
		for (TalentCategory tc : tcs) {
			tc.getTalent();
			System.out.println(tc.getTalent());
		}
		outer: for (String key : keys) {

			for (TalentCategory tc : tcs) {
				Set<Talent> talents = tc.getTalent();
				for (Talent t : talents) {
					if (t.getName().equalsIgnoreCase(key)) {
						talentInfo = new TalentInfo(key, map.get(key),
								t.getCategory());
						talentInfos.add(talentInfo);

					}
				}
			}

		}
		System.out.println(talentInfos);
		return talentInfos;
	}

	public static Map<String, String> sortMap(Map<String, String> unsortedMap) {
		Map<String, String> sortedMap = new HashMap<>();
		Set<String> keys = unsortedMap.keySet();
		List<String> k = new ArrayList<>();
		k.addAll(keys);
		int i = 1;
		while (k.size() > 0) {
			int val1 = Integer.parseInt(unsortedMap.get(k.get(0)));
			int val2 = Integer.parseInt(unsortedMap.get(k.get(i)));

			for (int j = i; j < k.size();) {

				if (val1 > val2) {
					val2 = Integer.parseInt(unsortedMap.get(k.get(++j)));
				} else {
					val1 = val2;
					val2 = Integer.parseInt(unsortedMap.get(k.get(++j)));
				}
			}
		}
		return sortedMap;
	}

	public static CareerClusterReport getCareerClusterReport(String userID) {
		Map<String, String> map = ProfileHelper.getClusters(userID, true);
		CareerClusterReport report = new CareerClusterReport(map);
		return report;
	}

	public static boolean sendPayLoad(HttpServletRequest req) {

		// 1. Read all posted request parameters
		String params = IpnHandler.getAllRequestParams(req);
		LOGGER.info(params);

		// 2. Prepare 'notify-validate' command with exactly the same
		// parameters
		Enumeration en = req.getParameterNames();
		StringBuilder cmd = new StringBuilder("cmd=_notify-validate");
		String paramName;
		String paramValue;
		String res = null;
		while (en.hasMoreElements()) {
			paramName = (String) en.nextElement();
			paramValue = req.getParameter(paramName);
			try {
				cmd.append("&").append(paramName).append("=")
						.append(URLEncoder.encode(paramValue, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// 3. Post above command to Paypal IPN URL
		FetchOptions options = FetchOptions.Builder.withDeadline(60.00);
		HTTPRequest outRequest = null;
		try {
			outRequest = new HTTPRequest(new URL(
					StringConstants.PAYPAY_PRODUCTION_URL), HTTPMethod.POST,
					options);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HTTPHeader contentType = new HTTPHeader("Content-Type",
				"application/x-www-form-urlencoded");
		outRequest.setHeader(contentType);
		outRequest
				.setPayload(cmd.toString().getBytes(Charset.forName("utf-8")));
		URLFetchService fs = URLFetchServiceFactory.getURLFetchService();
		try {
			HTTPResponse response = fs.fetch(outRequest);
			res = (new String(response.getContent()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IpnHandler handler = new IpnHandler();
		boolean complete = false;
		try {
			IpnInfo info = handler.handleIpn(res, req, params);
			if (info == null) {
				complete = false;
			} else {
				complete = true;
			}
		} catch (IpnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return complete;
	}

	public static Object getGroupFromCache(Key k1) {
		Object o = null;
		o = GROUPS.get(k1);
		if (o == null) {
			Entity e = GeneralController.findByKey(k1);
			String kind = e.getKind();
			if (kind.equals(Collection.class.getSimpleName())) {
				Collection col = EntityConverter.entityToCollection(e);
				GROUPS.put(col.getId(), col);
				o = col;
			} else if (kind.equals(Community.class.getSimpleName())) {
				Community com = EntityConverter.entityToCommunity(e);
				GROUPS.put(com.getId(), com);
				o = com;
			}
			return o;

		} else {
			return o;
		}
	}

	public static Map<String, Object> getPreferredPosts(AzureUser user,
			int offset) {
		Map<String, Object> map = GeneralController.getPreferredPosts(user,
				offset);
		List<Discussion> articles = (List<Discussion>) map.get("post");
		List<DiscussionBean> beans = new ArrayList<>();
		for (Discussion a : articles) {
			beans.add(Util.toDiscussionBean(a, user));
		}
		Map<String, Object> nMap = new HashMap<>();
		nMap.put("post", beans);
		nMap.put("offset", map.get("offset"));
		return nMap;
	}

	public static DiscussionBean toDiscussionBean(Discussion a,
			AzureUser currentUser) {
		DiscussionBean d = new DiscussionBean();
		if (a != null) {
			if (a.getOwner() != null) {
				AzureUser u = new UserJpaController().findUser(a.getOwner()
						.getName());

				if (u.getValidity().equals("ADMIN")) {
					d.setAuthorImage("/images/admin-avatar/tav2.jpg");
					d.setAuthor("Admin");
				} else {
					d.setAuthorImage(u.getPicture());
					d.setAuthor(u.getFirstName() + " " + u.getLastName());
				}

				if (a.getLikers() != null
						&& a.getLikers().contains(
								KeyFactory.createKey(
										AzureUser.class.getSimpleName(),
										currentUser.getUserID()))) {
					d.setLiked(true);
					d.setLikes(a.getLikers().size());
				}
			} else {
				d.setAuthor("Career Explora");
				d.setAuthorImage("/images/male-unknown-user.jpg");
			}

			d.setBody(a.getBody().getValue());

			d.setPictureUrl(getPictureUrl(a.getImage()));
			d.setPostDate(new SimpleDateFormat("dd-MMM-yyyy").format(a
					.getDateCreated()));
			String text = a.getBody().getValue();
			if (text.length() > 400) {
				int i = text.indexOf(" ", 300);
				String aStr = text.substring(0, i);
				String bStr = text.substring(i);
				d.setRemainingSnippet(bStr);
				d.setSnippet(aStr);
			} else {
				d.setSnippet(text);
			}
			d.setTitle(a.getTitle());
			d.setWebkey(KeyFactory.keyToString(a.getId()));
			if (a.getLink() != null) {
				d.setLink(a.getLink());
			}

		}
		return d;
	}

	public static String getClassValue(String sClass) {
		String c = null;
		switch (sClass) {
		case "1":
			c = StringConstants.CLASS1;
			break;
		case "2":
			c = StringConstants.CLASS2;
			break;
		case "3":
			c = StringConstants.CLASS3;
			break;
		case "4":
			c = StringConstants.CLASS4;
			break;
		case "5":
			c = StringConstants.CLASS5;
			break;
		case "6":
			c = StringConstants.CLASS6;
			break;
		case "7":
			c = StringConstants.CLASS7;
			break;
		case "8":
			c = StringConstants.CLASS8;
			break;
		case "9":
			c = StringConstants.CLASS9;
			break;
		case "10":
			c = StringConstants.CLASS10;
			break;
		case "11":
			c = StringConstants.CLASS11;
			break;
		case "12":
			c = StringConstants.CLASS12;
			break;
		}
		return c;
	}

	public static String getPictureUrl(BlobKey key) {
		if (key == null) {
			return null;
		} else {
			ServingUrlOptions suo = ServingUrlOptions.Builder.withBlobKey(key);
			ImagesService is = ImagesServiceFactory.getImagesService();
			return is.getServingUrl(suo);
		}
	}

	public static List<String> toInterestValues(List<String> ints) {
		List<String> list = new ArrayList<>();
		for (String s : ints) {
			switch (s) {
			case "1":
				list.add("Art");
				break;
			case "2":
				list.add("Commercial");
				break;
			case "3":
				list.add("Science");
				break;
			}
		}
		return list;
	}

	public static boolean updateMITS(HttpSession session, String TEST_NAME,
			String testDate, Text types) {
		AzureUser user = getUserFromSession(session);
		Record record = new Record(user.getUserID(), TEST_NAME, testDate, types);

		RecordJpaController cont = new RecordJpaController();
		Record r = cont.findRecord(record.getKey());

		if (r != null) {
			r.setMitTypes(types);
			updateNewRecord(r, cont);
		} else {
			saveNewRecord(record, cont);
		}
		return true;

	}

	public static boolean updateTalent(HttpSession session, String TEST_NAME,
			String testDate, Map<String, String> map) {
		AzureUser user = getUserFromSession(session);
		Record record = new Record(user.getUserID(), TEST_NAME, testDate, map);

		RecordJpaController cont = new RecordJpaController();
		Record r = cont.findRecord(record.getKey());

		if (r != null) {
			Map<String, String> m = r.getTalents();
			if (m != null) {
				m.putAll(map);
				r.setTalents(m);
			} else {
				r.setTalents(map);
			}
			updateNewRecord(r, cont);
		} else {
			saveNewRecord(record, cont);
		}
		return true;

	}

	public static boolean updateCareerCluster(HttpSession session,
			String testName, String testDate, Map<String, String> map) {
		AzureUser user = getUserFromSession(session);
		Record record = new Record(user.getUserID(), testName, testDate, map);

		RecordJpaController cont = new RecordJpaController();
		Record r = cont.findRecord(record.getKey());

		if (r != null) {
			Map m = r.getCareerClusters();
			if (m != null) {
				m.putAll(map);
				r.setCareerClusters(m);
			} else {
				r.setCareerClusters(map);
			}
			updateNewRecord(r, cont);
		} else {
			saveNewRecord(record, cont);
		}
		return true;

	}

	public static boolean updateSkill(HttpSession session, String TEST_NAME,
			Map<String, String> allSkillRecords, String testDate) {
		AzureUser user = getUserFromSession(session);
		Record record = new Record(user.getUserID(), TEST_NAME, testDate);

		RecordJpaController cont = new RecordJpaController();
		Record r = cont.findRecord(record.getKey());

		if (r != null) {
			Map m = r.getSkills();
			if (m != null) {
				m.putAll(allSkillRecords);
				r.setSkills(m);
			} else {
				r.setSkills(allSkillRecords);
			}
			updateNewRecord(r, cont);
		} else {
			record.setSkills(allSkillRecords);
			saveNewRecord(record, cont);
		}
		return true;
	}

	public static void saveUserToIndex(AzureUser u) {
		String interest = "";
		for (String s : Util.toInterestValues(u.getAreaOfInterest())) {
			interest += s + " ";
		}
		Document doc = Document
				.newBuilder()
				.setId(KeyFactory.keyToString(u.getKey()))
				.addField(
						Field.newBuilder().setName("firstName")
								.setText(u.getFirstName()))
				.addField(
						Field.newBuilder().setName("lastName")
								.setText(u.getLastName()))
				.addField(
						Field.newBuilder().setName("class")
								.setText(u.getsClass()))
				.addField(
						Field.newBuilder().setName("school")
								.setText(u.getSchool()))
				.addField(
						Field.newBuilder().setName("picture")
								.setText(u.getPicture()))
				.addField(
						Field.newBuilder().setName("interest")
								.setText(interest)).build();
		SearchDocumentIndexService.indexDocument("PEOPLE", doc);

	}

	public static PeoplePageBean getPeoplePageBean(PeoplePageBean ppb,
			AzureUser u) {
		if (ppb.getCategory().equals("2")) {
			return getFriends(ppb, u);
		} else if (ppb.getCategory().equals("3")) {
			return getFollowing(ppb, u);
		} else {
			return getSuggestedPeople(ppb, u);
		}

	}

	public static PeoplePageBean getPeoplePageBean(String category, AzureUser u) {
		if (category.equals("2")) {
			return getFriends(null, u);
		} else if (category.equals("3")) {
			return getFollowing(null, u);
		} else {
			return getSuggestedPeople(null, u);
		}

	}

	private static PeoplePageBean getFollowing(PeoplePageBean ppb, AzureUser u) {
		if (ppb == null) {
			return getNewFollowing();
		} else {
			return updateFollowing(ppb, u);
		}
	}

	private static PeoplePageBean updateFollowing(PeoplePageBean ppb,
			AzureUser u) {
		// TODO Auto-generated method stub
		return null;
	}

	private static PeoplePageBean getNewFollowing() {
		// TODO Auto-generated method stub
		return null;
	}

	private static PeoplePageBean getFriends(PeoplePageBean ppb, AzureUser u) {
		QueryResultList<Entity> r = GeneralController.getFriends(ppb,u);
		ppb.setCursor(r.getCursor().toWebSafeString());
		List<Friends> friends = new ArrayList<>();
		
		for(Entity e : r) {
			friends.add(EntityConverter.entityToFriends(e));
		}
		
		List<Key> frs = new ArrayList<>();
		
		for(Friends f : friends) {
			for(Key k: f.getFriends()) {
				if(k!=u.getKey()) {
					frs.add(k);
				}
			}
		}
		
		//get Person using key
		return null;
	}

	

	

	private static PeoplePageBean getSuggestedPeople(PeoplePageBean ppb,
			AzureUser u) {

		int limit = 20;
		if (ppb == null) {
			ppb = new PeoplePageBean();

		}
		Results<ScoredDocument> result = (ppb.getCursor() == null) ? searchSuggestedPeople(
				limit, u, null) : searchSuggestedPeople(limit, u, Cursor
				.newBuilder().build(ppb.getCursor()));

		List<Person> people = new ArrayList<>();
		people = addSuggestPeople(people, result, u);
		people = removeFriends(people, u);
		while (people.size() < 20 && limit - result.getNumberReturned() == 0) {
			limit = 20 - people.size();
			result = searchSuggestedPeople(limit, u, result.getCursor());
			people = addSuggestPeople(people, result, u);
			people = removeFriends(people, u);
		}

		ppb.setPeople(people);
		ppb.setCategory("SUGGESTED");
		ppb.setCursor((result.getCursor() == null) ? null : result.getCursor()
				.toWebSafeString());
		return ppb;

	}

	private static Results<ScoredDocument> searchSuggestedPeople(int limit,
			AzureUser u, Cursor cursor) {

		String grade = u.getsClass();
		List<String> interest = u.getAreaOfInterest();

		for (String s : interest) {
			grade += " " + s;
		}

		grade = grade.trim().replace(" ", " OR ");
		QueryOptions options = null;
		if (cursor == null) {
			options = QueryOptions.newBuilder()
					.setCursor(Cursor.newBuilder().build()).setLimit(limit)
					.build();
		} else {
			options = QueryOptions.newBuilder().setCursor(cursor)
					.setLimit(limit).build();
		}
		Query query = Query.newBuilder().setOptions(options).build(grade);
		Results<ScoredDocument> result = SearchDocumentIndexService
				.retrieveDocuments("PEOPLE", query);
		return result;
	}

	private static List<Person> addSuggestPeople(List<Person> people,
			Results<ScoredDocument> result, AzureUser u) {
		for (ScoredDocument sd : result) {

			Person p = new Person();
			p.setGrade(sd.getOnlyField("class").getText());
			p.setInterest(sd.getOnlyField("interest").getText());
			p.setName(sd.getOnlyField("firstName").getText() + " "
					+ sd.getOnlyField("lastName").getText());
			p.setPicture(sd.getOnlyField("picture").getText());
			p.setWebKey(sd.getId());
			Key key = KeyFactory.stringToKey(p.getWebKey());
			if (u.getFriendsId() != null && u.getFriendsId().contains(key)) {
				p.setFriend(true);
			}

			if (u.getFollowing() != null && u.getFollowing().contains(key)) {
				p.setFollowing(true);
			}

			people.add(p);

		}
		return people;
	}

	private static List<Person> removeFriends(List<Person> people, AzureUser u) {
		Iterator<Person> it = people.iterator();

		while (it.hasNext()) {
			Person p = it.next();
			if (p.isFriend()
					| p.getWebKey().equals(KeyFactory.keyToString(u.getKey()))) {
				it.remove();
			}
		}
		return people;
	}

}
