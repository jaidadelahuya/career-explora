package com.jevalab.azure.profile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.helper.classes.EntityConverter;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class UpdateProfile extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7514279643470786794L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String firstName = req.getParameter("first-name");
		String lastName = req.getParameter("last-name");
		String birthDate = req.getParameter("birth-date");
		String gender = req.getParameter("gender");
		String grade = req.getParameter("class");
		String[] interest = req.getParameterValues("interest");
		String school = req.getParameter("school");
		String state = req.getParameter("state");
		String country = req.getParameter("country");
		String hobbies = req.getParameter("hobbies");
		String summary = req.getParameter("summary");

		HttpSession session = req.getSession();
		Object o = null;
		Object o1 = null;

		synchronized (session) {
			o = session.getAttribute("userProfile");
			o1 = session.getAttribute(StringConstants.AZURE_USER);
		}

		if (o != null && o1 != null) {
			if (!Util.notNull(firstName)) {
				session.setAttribute("profileError",
						"First Name cannot be empty");
				resp.sendRedirect(resp.encodeRedirectURL("/azure/profile/edit"));
				return;
			}
			if (!Util.notNull(lastName)) {
				session.setAttribute("profileError",
						"Last Name cannot be empty");
				resp.sendRedirect(resp.encodeRedirectURL("/azure/profile/edit"));
				return;
			}
			if (!Util.notNull(gender)) {
				session.setAttribute("profileError", "Select your gender");
				resp.sendRedirect(resp.encodeRedirectURL("/azure/profile/edit"));
				return;
			}
			if (!Util.notNull(grade)) {
				session.setAttribute("profileError", "Select your class");
				resp.sendRedirect(resp.encodeRedirectURL("/azure/profile/edit"));
				return;
			}
			if (!Util.notNull(interest)) {
				session.setAttribute("profileError",
						"Select at least one area of interest");
				resp.sendRedirect(resp.encodeRedirectURL("/azure/profile/edit"));
				return;
			}

			Date date = null;
			if (Util.notNull(birthDate)) {
				SimpleDateFormat format = new SimpleDateFormat(
						"dd MMMMMM, yyyy");
				try {
					date = format.parse(birthDate);
				} catch (ParseException e) {
					session.setAttribute("profileError",
							"Enter a valid date e.g 4 October, 2016.");
					resp.sendRedirect(resp
							.encodeRedirectURL("/azure/profile/edit"));
					return;
				}
			}

			UserProfile up = (UserProfile) o;
			AzureUser u = (AzureUser) o1;

			up.setFirstName(firstName);
			up.setLastName(lastName);
			up.setBirthDate(date);
			up.setGender(gender);
			up.setGrade(grade);
			up.setCountry(country);
			up.setSchool(school);
			up.setState(state);
			List<String> i = Util.toInterestValues(Arrays.asList(interest));
			String is = "";
			for (String s : i) {
				is += (s + ", ");
			}
			is = is.substring(0, is.lastIndexOf(","));
			up.setInterest(is);
			up.setHobbies(hobbies);
			up.setAbout(summary);

			boolean change = false;

			if (!firstName.trim().equalsIgnoreCase(u.getFirstName())) {
				change = true;
			}
			if (!lastName.trim().equalsIgnoreCase(u.getLastName())) {
				change = true;
			}
			if (!u.getsClass().equalsIgnoreCase(grade.trim())) {
				change = true;
			}
			if (!(u.getAreaOfInterest().containsAll(Arrays.asList(interest)) && u
					.getAreaOfInterest().size() == Arrays.asList(interest)
					.size())) {
				change = true;
			}

			u.setFirstName(firstName);
			u.setLastName(lastName);
			u.setGender(gender);
			u.setsClass(grade);
			u.setDateOfBirth(date);
			if (Util.notNull(summary)) {
				u.setAbout(new Text(summary));
			}
			u.setSchool(school);
			u.setCountry(country);
			u.setState(state);
			u.setAreaOfInterest(Arrays.asList(interest));
			if (Util.notNull(hobbies)) {
				u.setHobbies(new Text(hobbies));
			}

			if (true) {
				Util.saveUserToIndex(u);
			}
			synchronized (session) {
				session.setAttribute("userProfile", up);
				session.setAttribute(StringConstants.AZURE_USER, u);
			}

			GeneralController.create(EntityConverter.userToEntity(u));

			resp.sendRedirect(resp
					.encodeRedirectURL("/azure/profile/get?web-key="
							+ KeyFactory.keyToString(u.getKey())));

		}

	}

}
