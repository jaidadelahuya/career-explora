package com.jevalab.azure;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.helper.classes.LoginHelper;
import com.jevalab.helper.classes.StringConstants;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

public class FacebookHandlerServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String id = req.getParameter("name");
		String picture = req.getParameter("picture");
		String cover = req.getParameter("cover");
		String access = req.getParameter("access");
		
		String appSecret = getServletContext().getInitParameter(
				StringConstants.APP_SECRET);
		
		User me = null;
		try {
			 me = LoginHelper.getFacebookDetails(access,
						appSecret, Version.VERSION_2_2);
		} catch (FacebookOAuthException e) {
			resp.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT,
					e.getErrorUserMessage());
		}
		
		UserJpaController cont = new UserJpaController();
		AzureUser user = cont.findUser(id);
		
		if(user == null) {
			//user = new AzureUser(id);
			user.setPicture(picture);
			user.setCover(cover);
			if(me != null) {
				user = LoginHelper.initNewAzureUser(user, me);
			}
			HttpSession session = req.getSession();
			synchronized (session) {
				session.setAttribute(StringConstants.AZURE_USER, user);
			}
			RequestDispatcher rd = req.getRequestDispatcher("/authorization");
			rd.forward(req, resp);
			
		} else {
			user.setPicture(picture);
			user.setCover(cover);
			if(me != null) {
				user = LoginHelper.editExistingUser(me, user);
			}
			HttpSession session = req.getSession();
			synchronized (session) {
				session.setAttribute(StringConstants.AZURE_USER, user);
			}
			RequestDispatcher rd = req.getRequestDispatcher("/success");
			rd.forward(req, resp);
		}
		
	}
}
