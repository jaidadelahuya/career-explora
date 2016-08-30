package com.jevalab.azure;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.helper.classes.LoginHelper;
import com.jevalab.helper.classes.PasswordRecovery;
import com.jevalab.helper.classes.RegistrationForm;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.UserView;
import com.jevalab.helper.classes.WelcomePageBean;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		
		AzureUser user = null;
		synchronized (session) {
			user = (AzureUser) session.getAttribute(StringConstants.AZURE_USER);

		}

		WelcomePageBean wpb = LoginHelper.initWelcomePageBean(user);
		

		RegistrationForm rf = null;
		synchronized (session) {
			
			session.setAttribute(StringConstants.WELCOME_PAGE, wpb);
			rf = (RegistrationForm)session.getAttribute(StringConstants.REGISTRATION_FORM);
		}
		
		resp.sendRedirect("/azure/welcome");

		PasswordRecovery pr = null;
		if(rf != null) {
			pr = rf.getPasswordRecovery();
		}
		user = LoginHelper.persistUser(user, pr);
		

	}

}
