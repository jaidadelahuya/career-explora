package com.jevalab.azure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.helper.classes.LoginHelper;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class AddDefaultPicture extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3516832536704550752L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String type = req.getParameter("type");
		String url = req.getParameter("avatar");

	

		if (Util.notNull(url)) {
			
			HttpSession session = req.getSession();
			Object o = null;
			synchronized (session) {
				o  = session.getAttribute(StringConstants.AZURE_USER);
			}
			if(o!=null) {
				AzureUser u = (AzureUser) o;
				if ("profile".equals(type)) {
					u.setPicture(url);
					resp.getWriter().write(new Gson().toJson("cover"));
				} else if ("cover".equals(type)) {
					u.setCover(url);
					List<String> ints = u.getAreaOfInterest();
					String sClass = u.getsClass();
					String iStr = "";
					for (String s : ints) {
						iStr += ("&interest=" + s);
					}
					String c = "&class=" + sClass;
					
					String webkey = KeyFactory.createKeyString(
							AzureUser.class.getSimpleName(), u.getUserID());
					String data = resp.encodeRedirectURL("/azure/success?id=" + webkey
							+ iStr + c);
					u = LoginHelper.persistUser(u, null);
					Util.saveUserToIndex(u);
					resp.getWriter().write(new Gson().toJson(data.trim()));
				}
				synchronized (session) {
					session.setAttribute(StringConstants.AZURE_USER, u);
				}
			}
		} else {
			if ("profile".equals(type)) {
				resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,
						"Select an avatar for your profile picture");
			} else if ("cover".equals(type)) {
				resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED,
						"Select a cover picture for your profile background");
			}

		}

	}

}
