package com.jevalab.azure.people;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.azure.profile.UserProfile;
import com.jevalab.helper.classes.EntityConverter;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class AddFollowing extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -677733472955899060L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String webKey = req.getParameter("web-key");
		HttpSession session = req.getSession();
		Object o = null;
		Object o1 = null;
		synchronized (session) {
			o = session.getAttribute(StringConstants.AZURE_USER);
			o1 = session.getAttribute("peoplePageBean");
		}
		if (Util.notNull(webKey) && o != null && o1!=null) {
			Key key = KeyFactory.stringToKey(webKey);
			AzureUser u = (AzureUser) o;
			AzureUser user = EntityConverter.entityToUser(GeneralController
					.findByKey(key));
			List<Key> followers = user.getFollowers();
			if (followers == null) {
				followers = new ArrayList<>();
			}
			if (!followers.contains(u.getKey())) {
				followers.add(u.getKey());
			}
			user.setFollowers(followers);
			List<Key> following = u.getFollowing();
			if (following == null) {
				following = new ArrayList<>();
			}
			if (!following.contains(key)) {
				following.add(key);
			}
			u.setFollowing(following);

			GeneralController.createWithCrossGroup(EntityConverter.userToEntity(u),
					EntityConverter.userToEntity(user));
			PeoplePageBean ppb = (PeoplePageBean) o1;
			List<Person> people = ppb.getPeople();
			for(Person p: people) {
				if(p.getWebKey().equals(webKey)) {
					p.setFollowing(true);
				}
			}
			
			ppb.setPeople(people);
			
			synchronized (session) {
				session.setAttribute(StringConstants.AZURE_USER, u);
				session.setAttribute("peoplePageBean", ppb);
			}
			

		} else {
			req.getRequestDispatcher("/session/invalid").include(req, resp);
		}
	}

}
