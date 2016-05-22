package com.jevalab.azure;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.exceptions.InvalidLoginException;
import com.jevalab.helper.classes.PasswordRecovery;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.UserSettingsModel;
import com.jevalab.helper.classes.Util;

public class SettingsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1782386129186915935L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		if(session.isNew()) {
			throw new InvalidLoginException();
		} else {
			Object o= null;
			synchronized (session) {
				o = session.getAttribute(StringConstants.AZURE_USER);
			}
			if(o == null) {
				throw new InvalidLoginException();
			} else {
				AzureUser user = (AzureUser) o;
				boolean hasPassword = (user.getPassword()==null)?false:true;
				boolean hasFacebook = Util.isFacebookID(user.getUserID());
				PasswordRecovery mobile = Util.getMobilePasswordRecovery(user.getPasswordRecoveryIds());
				PasswordRecovery email = Util.getEmailPasswordRecovery(user.getPasswordRecoveryIds());
				PasswordRecovery altMobile = Util.getAltMobilePasswordRecovery(user.getPasswordRecoveryIds());
				PasswordRecovery altEmail = Util.getAltEmailPasswordRecovery(user.getPasswordRecoveryIds());
				UserSettingsModel usm = new UserSettingsModel(hasFacebook, hasPassword, mobile, altMobile, email, altEmail);
				usm.addPropertyChangeListener(user);
				synchronized (session) {
					session.setAttribute(StringConstants.USER_SETTINGS_MODEL, usm);
				}
				RequestDispatcher rd=req.getRequestDispatcher("/azure/settings");
				rd.forward(req, resp);
			}
			
			
		}
	}
}
