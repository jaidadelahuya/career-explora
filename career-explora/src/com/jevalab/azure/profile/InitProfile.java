package com.jevalab.azure.profile;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.KeyFactory;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.helper.classes.MainNav;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class InitProfile extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5742375807928510151L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String editting = req.getParameter("edit");
		Object o1 = null;
		synchronized (session) {
			session.removeAttribute("profileError");
			Object o = session.getAttribute("mainNav");
			o1 = session.getAttribute(StringConstants.AZURE_USER);
			if (o != null) {
				MainNav mn = (MainNav) o;
				mn.setProfile(Boolean.valueOf(true));
				session.setAttribute("mainNav", mn);
			}

		}

		if (o1 == null) {
			return;
		} else if(Util.notNull(editting)) {
			resp.sendRedirect(resp.encodeRedirectURL("/azure/profile/edit"));
		}else  {
			AzureUser u = (AzureUser) o1;

			resp.sendRedirect(resp.encodeRedirectURL("/azure/profile/get?web-key="
							+ KeyFactory.keyToString(u.getKey())));
		}

	}
}
