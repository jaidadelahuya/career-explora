package com.jevalab.azure;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.jevalab.helper.classes.ModuleInfo;
import com.jevalab.helper.classes.ProfileHelper;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.UserProfile;

public class MoreProfileInfoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	protected void doPost(javax.servlet.http.HttpServletRequest req,
			javax.servlet.http.HttpServletResponse resp)
			throws javax.servlet.ServletException, java.io.IOException {

		HttpSession session = req.getSession();

		if (!session.isNew()) {
			String profileName = req.getParameter("name");
			Object o = session.getAttribute(StringConstants.USER_PROFILE);
			UserProfile profile = null;
			if (o != null) {
				profile = (UserProfile) o;
				ModuleInfo mi = profile.getModuleInfo(profileName);
				mi = ProfileHelper.getSixMore(mi);
				profile.setCounter(profileName, mi.getCounter());
				
				synchronized (session) {
					session.setAttribute(StringConstants.USER_PROFILE, profile);
				}
				
				resp.setContentType("application/json");
				String json = null; 
				json = new Gson().toJson(mi.getList()).toString();
				System.out.println(json); 
				resp.getWriter().write(json);
			}

		}

	}
}
