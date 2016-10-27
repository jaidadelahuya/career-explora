package com.jevalab.azure.profile;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.helper.classes.EntityConverter;
import com.jevalab.helper.classes.MainNav;
import com.jevalab.helper.classes.ProfileHelper;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class GetProfile extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2256897579487519346L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String webKey  = req.getParameter("web-key");
		
		Object o = null;
		
		HttpSession session = req.getSession();
		
		synchronized (session) {
			o = session.getAttribute(StringConstants.AZURE_USER);
		}
		
		if(o!= null && Util.notNull(webKey)) {
			AzureUser u = (AzureUser) o;
			UserProfile up = new UserProfile();
			if(!u.getKey().equals(KeyFactory.stringToKey(webKey))) {
				Entity e= GeneralController.findByKey(KeyFactory.stringToKey(webKey));
				u = EntityConverter.entityToUser(e);
				Object o1 = null;
				synchronized (session) {
					o1 = session.getAttribute("mainNav");
					if(o1!=null) {
						MainNav mv = (MainNav) o1;
						mv.setNone();
						session.setAttribute("mainNav", mv);
					}
					
				}
				
				
				
				
			}else {
				up.setCurrentUser(true);
			}
			
			up = ProfileHelper.getProfileData(u, up);
			
			synchronized (session) {
				session.setAttribute("userProfile", up);
			}
			
			req.getRequestDispatcher("/WEB-INF/profile/index.jsp")
			.include(req, resp);
			
			
		}
	}

}
