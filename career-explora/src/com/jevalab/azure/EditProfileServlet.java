package com.jevalab.azure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.profile.UserProfile;
import com.jevalab.exceptions.InvalidLoginException;
import com.jevalab.helper.classes.StringConstants;


public class EditProfileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2042723459590485658L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		if(session.isNew()) {
			throw new InvalidLoginException();
		} else {
			
			Object o = null;
			Object o1 = null;
			synchronized (session) {
				o = session.getAttribute(StringConstants.AZURE_USER);
				o1 = session.getAttribute(StringConstants.USER_PROFILE);
			}
			
			if(o != null && o1 != null) {
				
				AzureUser user = (AzureUser) o;
			
				UserProfile profile = (UserProfile) o1;
				
				String propertyType = req.getParameter("propertyType");
				String property = req.getParameter("property");
				
				if(propertyType.equalsIgnoreCase("profile-first-name")) {
					user.setFirstName(property);
					profile.setFirstName(property);
				} else if(propertyType.equalsIgnoreCase("profile-last-name")) {
					user.setLastName(property);
					profile.setLastName(property);
				} else if(propertyType.equalsIgnoreCase("profile-middle-name")) {
					user.setMiddleName(property);
					profile.setMiddleName(property);
				} else if(propertyType.equalsIgnoreCase("profile-gender")) {
					user.setGender(property);
					profile.setGender(property);
				} else if(propertyType.equalsIgnoreCase("profile-email")) {
					user.setEmail(property);
					profile.setEmail(property);
				} else if(propertyType.equalsIgnoreCase("profile-school")) {
					user.setSchool(property);
					profile.setSchool(property);
				} else if(propertyType.equalsIgnoreCase("profile-lives-in")) {
					String state = req.getParameter("state");
					String country = req.getParameter("country");
					user.setState(state);
					user.setCountry(country);
					profile.setState(state);
					profile.setCountry(country);
				}  
				
				synchronized (session) {
					session.setAttribute(StringConstants.AZURE_USER, user);
					session.setAttribute(StringConstants.USER_PROFILE, profile);
				}
			} else {
				throw new InvalidLoginException();
			}
			
			
		}
	}
}
