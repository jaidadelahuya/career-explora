package com.jevalab.azure;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.exceptions.InvalidLoginException;
import com.jevalab.helper.classes.LoginHelper;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.UserView;
import com.jevalab.helper.classes.Util;


public class MoreFriendsServlet extends HttpServlet {

	protected void doGet(javax.servlet.http.HttpServletRequest req,
			javax.servlet.http.HttpServletResponse resp)
			throws javax.servlet.ServletException, java.io.IOException {
		
		HttpSession session = req.getSession();
		List<UserView> sf = null;
		if (!session.isNew()) {
			AzureUser user =  (AzureUser)session.getAttribute(StringConstants.AZURE_USER);
			sf = LoginHelper.getSuggestedFriends(user, session);
			session.setAttribute(StringConstants.SUGGESTED_fRIENDS_VIEW, sf);
			String json = Util.toJsonString(sf);
			resp.setContentType("application/json");
			System.out.println(json);
			resp.getWriter().write(json);
		} else {
			throw new InvalidLoginException();
		}
		
		
	}
}
