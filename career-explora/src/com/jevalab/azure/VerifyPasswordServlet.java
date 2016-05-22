package com.jevalab.azure;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.datanucleus.jta.ResinTransactionManagerLocator;

import com.jevalab.exceptions.InvalidLoginException;
import com.jevalab.helper.classes.RegistrationForm;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;
import com.twilio.sdk.TwilioRestException;

public class VerifyPasswordServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.isNew()) {
			throw new InvalidLoginException();
		} else {
			String id = req.getParameter("id");
			Boolean codeSent = new Boolean(req.getParameter("codeSent"));
			String cc = "confirmaionCode";
			String code = Util.generateRandomCode(100000, 900000);
			if (codeSent) {
				String iCode = req.getParameter("code");
				synchronized (session) {
					code = (String) session.getAttribute(cc);
				}
				if (code.equals(iCode.trim())) {
					resp.setStatus(HttpServletResponse.SC_OK);
					session.removeAttribute(cc);
				} else {
					resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,
							"You entered a wrong confirmation code.");
				}
				session.removeAttribute(cc);
			} else {

				if (Util.isEmail(id)) {
					try {
						Util.sendEmail(id,
								StringConstants.CONFIRMATION_EMAIL_SUBJECT,
								StringConstants.CONFIRMATION_EMAIL_BODY + code);
						synchronized (session) {
							session.setAttribute(cc, code);
						}
					} catch (Exception e) {
						resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,
								"We could not send an email your email address, try another email or use your mobile number.");
						e.printStackTrace();
					} 
				} else if (Util.isNumeric(id)) {
					try {
						String accSID = getServletContext().getInitParameter(
								StringConstants.TWILIO_SID);
						String authToken = getServletContext()
								.getInitParameter(
										StringConstants.TWILIO_AUTH_TOKEN);
						Util.sendSMS(accSID,
								authToken, code);
						synchronized (session) {
							session.setAttribute(cc, code);
						}
					} catch (Exception e) {
						resp.sendError(
								HttpServletResponse.SC_BAD_REQUEST,
								"We could not send SMS to your mobile number, try another number or use your email address.");
						e.printStackTrace();
					}
				}
			}

		}
	}
}
