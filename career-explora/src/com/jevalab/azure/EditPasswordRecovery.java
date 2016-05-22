package com.jevalab.azure;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.exceptions.InvalidLoginException;
import com.jevalab.helper.classes.PasswordRecovery;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.UserSettingsModel;
import com.jevalab.helper.classes.Util;

public class EditPasswordRecovery extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2169427616850493974L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		if(session.isNew()) {
			throw new InvalidLoginException();
		} else {
			String oldId = req.getParameter("old");
			String newId = req.getParameter("nw");
			
			UserSettingsModel usm = null;
			Object o = null;
			
			synchronized (session) {
				o=session.getAttribute(StringConstants.USER_SETTINGS_MODEL);
			}
			
			if(o instanceof UserSettingsModel) {
				usm = (UserSettingsModel) o;
			}
			
			Map<String, PasswordRecovery> map = Util.oldAndNewPasswordRecovery(oldId,newId,usm.getPasswordRecoveries());
			boolean reset = usm.resetPasswordRecovery(map.get("opr"),map.get("npr"));
			resp.setContentType("application/json");
		}
		
		
		
	}
}
