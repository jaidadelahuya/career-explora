package com.jevalab.azure;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.helper.classes.PasswordRecovery;
import com.jevalab.helper.classes.RegistrationForm;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;
import com.twilio.sdk.TwilioRestException;

public class RegisterUserServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String firstName = req.getParameter("first-name");
		String lastName = req.getParameter("last-name");
		String usernameMode = req.getParameter("username-mode");
		String username = req.getParameter("userid");
		String password1 = req.getParameter("password1");
		String password2 = req.getParameter("password2");
		String gender = req.getParameter("gender");
		String passWordRecoveryNumber = req.getParameter("password-recovery");
		Boolean usingUsername = new Boolean(req.getParameter("using-username"));

		boolean usingEmail = false;
		boolean usingPhone = false;

		if (usingUsername != null) {
			if (usingUsername) {
				username = username.toLowerCase();
				PasswordRecovery pr = new PasswordRecovery(passWordRecoveryNumber, false, false, true, true,false);
				RegistrationForm rf = new RegistrationForm(firstName, lastName, username, password1, password2, gender, pr);
				Map<String, String> map = Util.validateRegistrationForm(rf);
				
				if (map.containsKey(StringConstants.ERROR)) {
					resp = Util.sendAjaxErrorMessage(map.get(StringConstants.ERROR), resp);
					return;
				} else {
					AzureUser user = new AzureUser(rf);
					HttpSession session = req.getSession();
					synchronized (session) {
						session.setAttribute(StringConstants.AZURE_USER, user);
						session.setAttribute(StringConstants.REGISTRATION_FORM, rf);
					}
					
					resp.setContentType("text/html");
					resp.getWriter().write("/authorization");
				}
			} else {
				if (usernameMode.equalsIgnoreCase("email")) {
					usingEmail = true;
				} else {
					usingPhone = true;
				}
				PasswordRecovery pr = new PasswordRecovery(username, false, usingEmail, usingPhone, true, true);
				RegistrationForm rf = new RegistrationForm(firstName, lastName,
						username, password1, password2, gender, usingPhone,
						usingEmail,pr);
				System.out.println(rf.getConfirmationCode());
				Map<String, String> map = Util.validateRegistrationForm(rf);

				if (map.containsKey(StringConstants.ERROR)) {
					resp = Util.sendAjaxErrorMessage(map.get(StringConstants.ERROR), resp);
					return;
				} else {
					if (usingPhone) {
						String accSID = getServletContext().getInitParameter(
								StringConstants.TWILIO_SID);
						String authToken = getServletContext()
								.getInitParameter(
										StringConstants.TWILIO_AUTH_TOKEN);

						try {
							Util.sendSMS(accSID, authToken, rf);
						} catch (TwilioRestException e) {
							e.printStackTrace();
							
							resp = Util.sendAjaxErrorMessage("We could not send SMS to your mobile number, try another number or use your email address.", resp);
							return;
						} catch (Exception e) {
							e.printStackTrace();
							resp = Util.sendAjaxErrorMessage("We could not send SMS to your mobile number, try another number or use your email address.", resp);
							return;
						}

					} else {
						String body = StringConstants.CONFIRMATION_EMAIL_BODY
								+ rf.getConfirmationCode();
						try {
							Util.sendEmail(rf.getUsername(),
									StringConstants.CONFIRMATION_EMAIL_SUBJECT,
									body);
						} catch (AddressException e) {
							e.printStackTrace();
							resp = Util.sendAjaxErrorMessage("We could not send an email your email address, try another email or use your mobile number.", resp);
							return;
						} catch (MessagingException e) {
							e.printStackTrace();
							resp = Util.sendAjaxErrorMessage("We could not send an email your email address, try another email or use your mobile number.", resp);
							return;
						} catch (Exception e) {
							e.printStackTrace();
							resp = Util.sendAjaxErrorMessage("We could not send an email your email address, try another email or use your mobile number.", resp);
							return;
						}
					}

					HttpSession session = req.getSession();
					synchronized (session) {
						session.setAttribute(StringConstants.REGISTRATION_FORM,
								rf);
					}
					resp.setContentType("text/html");
					resp.getWriter().write("/confirmation-code");
				}
			}
		}
	}
}
