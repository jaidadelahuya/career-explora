package com.jevalab.azure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.helper.classes.PasswordRecovery;
import com.jevalab.helper.classes.RegistrationForm;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class PasswordRecoveryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6987139756567310859L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String username = req.getParameter("username");

		HttpSession session = req.getSession();
		if (session.isNew()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			UserJpaController c1 = new UserJpaController();
			AzureUser user = c1.findUserByUsername(username, false);
			if (user == null) {
				if (Util.isNumeric(username)) {
					resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,
							"The mobile number you entered does not belong to any account.");
				} else if (Util.isEmail(username)) {
					resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,
							"The email address you entered does not belong to any account.");
				} else {
					resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,
							"The username you entered does not belong to any account.");
				}

			} else {

				List<PasswordRecovery> list = Util
						.getPasswordRecoveryListFromUser(user, c1, username,
								false);

				PasswordRecovery dpr = Util.getDefaultPasswordRecovery(list);

				RegistrationForm rf = new RegistrationForm(dpr.getKey()
						.getName(), dpr.isMobile(), dpr.isEmail());

				boolean codeSent = Util
						.sendConfirmationToDefaultPasswordRecovery(dpr, rf);
				List<PasswordRecovery> vList = Util
						.getPasswordRecoveryListFromUser(user, c1, username,
								true);

				if (codeSent) {
					session = req.getSession();
					synchronized (session) {
						session.setAttribute(StringConstants.AZURE_USER, user);
						session.setAttribute(StringConstants.REGISTRATION_FORM,
								rf);
						session.setAttribute(
								StringConstants.FROM_PASSWORD_RECOVERY, true);
						session.setAttribute(
								StringConstants.PASSWORD_RECOVERY_LIST, vList);
					}

					resp.setContentType("text/html");
					resp.getWriter().write("/confirmation-code");
				} else {
					resp.sendError(
							HttpServletResponse.SC_EXPECTATION_FAILED,
							"You do not have a verified password recovery email address or mobile number.Please Consult the admin");
				}

			}

		}
	}

}
