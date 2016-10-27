/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.group1;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.Discussion;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.helper.classes.DiscussionBean;
import com.jevalab.helper.classes.EntityConverter;
import com.jevalab.helper.classes.Util;
import com.jevalab.helper.classes.WelcomePageBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddComment extends HttpServlet {
	private static final long serialVersionUID = 708533363830459699L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String webKey = req.getParameter("web-key");
		String comm = req.getParameter("comment");
		Object o = null;
		Object o1 = null;

		HttpSession session = req.getSession();

		synchronized (session) {
			o = session.getAttribute("azureUser");
			o1 = session.getAttribute("welcomePage");
		}

		if ((Util.notNull(new String[] { webKey })) && (o != null)
				&& (o1 != null)) {
			if (Util.notNull(new String[] { comm })) {
				AzureUser u = (AzureUser) o;
				WelcomePageBean wpb = (WelcomePageBean) o1;

				Key key = KeyFactory.stringToKey(webKey);

				Entity e = GeneralController.findByKey(key);
				if (e == null)
					return;
				Discussion d = EntityConverter.entityToDiscussion(e);
				List<Key> subs = d.getSubscribers();
				if (subs == null) {
					subs = new ArrayList<>();
				}

				if (!(subs.contains(KeyFactory.createKey(
						AzureUser.class.getSimpleName(), u.getUserID())))) {
					subs.add(KeyFactory.createKey(
							AzureUser.class.getSimpleName(), u.getUserID()));
				}
				d.setComments(d.getComments() + 1L);

				d.setSubscribers(subs);
				Discussion comment = new Discussion();

				comment.setOwner(KeyFactory.createKey(
						AzureUser.class.getSimpleName(), u.getUserID()));
				comment.setDateCreated(new Date());
				comment.setParent(d.getId());
				comment.setBody(new Text(comm));

				GeneralController.createWithCrossGroup(new Entity[] {
						EntityConverter.discussionToEntity(d),
						EntityConverter.discussionToEntity(comment) });

				List<DiscussionBean> dbs = wpb.getPosts();
				for (DiscussionBean db : dbs) {
					if (db.getWebkey().equals(webKey)) {
						db.setComments(db.getComments() + 1L);
						break;
					}
				}

				wpb.setPosts(dbs);

				synchronized (session) {
					session.setAttribute("welcomePage", wpb);
				}
			}

		} else
			req.getRequestDispatcher("/session/invalid").forward(req, resp);
	}
}