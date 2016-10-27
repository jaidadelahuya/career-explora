package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.appengine.api.datastore.Text;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.Record;
import com.jevalab.azure.persistence.RecordJpaController;
import com.jevalab.azure.persistence.SkillCategory;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.azure.profile.UserProfile;
import com.jevalab.helper.classes.ProfileHelper;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.UserView;

public class UserProfileServlet extends HttpServlet {

	/**
	 * this class is poorly optimized. the method calls consume cpu
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Map map =req.getParameterMap();
		String id = req.getParameter("id");
		AzureUser user = null;
		HttpSession session = req.getSession();

		if (session.isNew()) {
			return;
		} else {
			
			Object o = null;
			Object o1 = null;
			synchronized (session) {
				o1= session.getAttribute(StringConstants.AZURE_USER);
				o = session.getAttribute(StringConstants.USER_PROFILE);
			}
			
			if(o1 != null && o1 instanceof AzureUser) {
				user = (AzureUser) o1;
				boolean currentUser;
				if(id.trim().equals("11111")) {
					currentUser = true;
				} else {
					currentUser = false;
					List<UserView> suggestedFriends = null;
					synchronized (session) {
						suggestedFriends = (List<UserView>) session.getAttribute(StringConstants.SUGGESTED_fRIENDS_VIEW);
					}
					
					if(suggestedFriends != null) {
						UserView uv = suggestedFriends.get(new Integer(id.trim()));
						String uid = uv.getId();
						user = ProfileHelper.getProfileUser(uid);
					}
					
				}
				  
				UserProfile userProfile = ProfileHelper.getProfileData(user);
				synchronized (session) {
					session.setAttribute(StringConstants.USER_PROFILE, userProfile);
				}
				
				
				RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/profile.jsp");
				rd.forward(req, resp);
			} else {
				resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			}
			
			
			
			
			

		}

	}
}
