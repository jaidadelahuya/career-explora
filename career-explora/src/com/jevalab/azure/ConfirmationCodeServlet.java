package com.jevalab.azure;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.cloud.sql.jdbc.internal.Util;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.helper.classes.RegistrationForm;
import com.jevalab.helper.classes.StringConstants;

public class ConfirmationCodeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7075740653359943341L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();

		resp.setContentType("text/html");
		String code = req.getParameter("confirmation-code").trim();
		if (code == null) {
			resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,
					"Please enter your confirmation code.");
		} else {
			if (!session.isNew()) {

				RegistrationForm rf = null;
				Object o = null;
				synchronized (session) {
					o = session.getAttribute(StringConstants.REGISTRATION_FORM);
				}
				if (o != null) {
					rf = (RegistrationForm) o;

					if (code.equals(rf.getConfirmationCode())) {
						if (rf.getPasswordRecovery() == null) {
							resp.getWriter().write("/reset-password");

						} else {
							rf.getPasswordRecovery().setVerified(true);
							AzureUser user = new AzureUser(rf);
							user.setAuthorized(true);
							user.setSubscriptionDate(new Date());
							user.setNewUser(true);
							synchronized (session) {
								session.setAttribute(
										StringConstants.AZURE_USER, user);
							}

							resp.getWriter().write("/azure/interest/select");
							UserJpaController cont = new UserJpaController();
							cont.create(user, rf.getPasswordRecovery());

						}

					} else {
						resp.sendError(
								HttpServletResponse.SC_EXPECTATION_FAILED,
								"You entered a wrong confirmation code");
					}
				}
			} else {
				resp.sendRedirect("/index.jsp");
			}

		}

	}
}
