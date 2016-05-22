package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class PasswordResetServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7209400061271613893L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.isNew()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			String password1 = req.getParameter("password1");
			String password2 = req.getParameter("password2");
			String withOldPassword = req.getParameter("with-old-password");
			Boolean wop = new Boolean(withOldPassword);
			String oldPassword = null;
			
			Object o = null;
			synchronized (session) {
				o = session.getAttribute(StringConstants.AZURE_USER);
			}
			
			if(wop) {
				oldPassword = req.getParameter("old-password");
				if(o != null) {
					AzureUser user = (AzureUser) o;
					if(!user.getPassword().equals(oldPassword)) {
						resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
								"You have enter a wrong password for this account.");
						return;
					}
				}
				
			}
			
			if (password1 == null || password1.isEmpty()) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"Please enter a password.");
				return;
			} else if (password1.length() < 7 | password1.length() > 21) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"Your password should have between 7 to 21 characters.");
				return;
			} else if (!(Util.isValidpassword(password1))) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"Your password should contain at least a digit and a special character.");
					return;
			} else if (password2 == null || password2.isEmpty()) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"Please re-enter a password.");
				return;
			} else if (!(password1.equals(password2))) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
						"Passwords do not match.");
				return;
			}

			
			if (o != null) {
				AzureUser user = (AzureUser) o;
				UserJpaController c1 = new UserJpaController();
				List<String> oldPasswords = user.getOldPasswords();
				if(oldPasswords == null) {
					oldPasswords = new ArrayList<>();
				}
				if(oldPasswords.contains(password1)) {
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
							"You have used the password you entered recently, Please use another password.");
					return;
				} else {
					if(oldPasswords.size() >= 5) {
						oldPasswords.remove(0);
					}
					oldPasswords.add(password1);
				}
				user.setPassword(password1);
				user.setOldPasswords(oldPasswords);
				user.setLastPasswordChangeDate(new Date());
				synchronized (session) {
					session.setAttribute(StringConstants.AZURE_USER, user);
				}
				c1.create(user, null);
				resp.getWriter().write("/login-page");
				return;
			} else {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

		}
	}
}
