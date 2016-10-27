/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.group1;

import com.google.appengine.api.datastore.KeyFactory;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.helper.classes.DiscussionBean;
import com.jevalab.helper.classes.EntityConverter;
import com.jevalab.helper.classes.MainNav;
import com.jevalab.helper.classes.Util;
import com.jevalab.helper.classes.WelcomePageBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPost extends HttpServlet {
	private static final long serialVersionUID = -1928433403216551463L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String webkey = req.getParameter("webkey");
		String bean = req.getParameter("bean");
		HttpSession session = req.getSession();
		if (Util.notNull(new String[] { webkey, bean })) {
			Object o = null;
			Object o1 = null;
			Object o2 = null;
			synchronized (session) {
				o1 = session.getAttribute("azureUser");
				o2 = session.getAttribute("mainNav");
				if (bean.equalsIgnoreCase("welcome")) {
					o = session.getAttribute("welcomePage");
				}

			}

			if ((o != null) && (o1 != null) && (o2 != null)) {
				MainNav mn = (MainNav) o2;
				mn.setNone();
				AzureUser u = (AzureUser) o1;
				List<DiscussionBean> list = null;
				if (o instanceof WelcomePageBean) {
					WelcomePageBean wpb = (WelcomePageBean) o;
					list = wpb.getPosts();
				}
				DiscussionBean d = null;
				for (DiscussionBean db : list) {
					if (db.getWebkey().equals(webkey)) {
						db = d;
						break;
					}
				}

				if (d == null) {
					d = Util.toDiscussionBean(
							EntityConverter
									.entityToDiscussion(GeneralController
											.findByKey(KeyFactory
													.stringToKey(webkey))), u);
				}
				synchronized (session) {
					session.setAttribute("post", d);
					session.setAttribute("mainNav", mn);
				}

				req.getRequestDispatcher("/azure/post").include(req, resp);

				return;
			}
			req.getRequestDispatcher("/session/invalid").forward(req, resp);
			return;
		}

		req.getRequestDispatcher("/session/invalid").forward(req, resp);
	}
}