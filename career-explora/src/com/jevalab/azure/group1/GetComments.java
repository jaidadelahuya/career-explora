/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.group1;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.helper.classes.DiscussionBean;
import com.jevalab.helper.classes.EntityConverter;
import com.jevalab.helper.classes.Util;
import com.jevalab.helper.classes.WelcomePageBean;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetComments extends HttpServlet {
	private static final long serialVersionUID = -5878691294978051804L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String parentWebKey = req.getParameter("webkey");
		HttpSession session = req.getSession();
		Object o = null;
		Object o1 = null;
		synchronized (session) {
			o = session.getAttribute("welcomePage");
			o1 = session.getAttribute("azureUser");
		}
		if ((Util.notNull(new String[] { parentWebKey })) && (o != null)
				&& (o1 != null)) {
			WelcomePageBean wpb = (WelcomePageBean) o;
			AzureUser u = (AzureUser) o1;
			List<DiscussionBean> dbs = wpb.getPosts();
			for (DiscussionBean d : dbs)
				if (d.getWebkey().equals(parentWebKey)) {
					Key parKey = KeyFactory.stringToKey(parentWebKey);
					QueryResultList<Entity> qr = GeneralController.getComments(parKey,
							d.getCommentCursor());
					
					if (qr.size() < 10)
						d.setCommentCursor(null);
					else {
						d.setCommentCursor(qr.getCursor().toWebSafeString());
					}

					wpb.setPosts(dbs);
					synchronized (session) {
						session.setAttribute("welcomePage", wpb);
					}
					List<DiscussionBean> beans = new ArrayList<>();
					for (Entity e : qr) {
						beans.add(Util.toDiscussionBean(
								EntityConverter.entityToDiscussion(e), u));
					}
					Gson gson = new Gson();
					resp.setContentType("application/json");
					resp.getWriter().write(gson.toJson(beans));
					return;
				}
		} else {
			req.getRequestDispatcher("/session/invalid").forward(req, resp);
		}
	}
}