package com.jevalab.azure.group1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.Discussion;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.helper.classes.DiscussionBean;
import com.jevalab.helper.classes.EntityConverter;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;
import com.jevalab.helper.classes.WelcomePageBean;

public class AddComment extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 708533363830459699L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String webKey = req.getParameter("web-key");
		String comm = req.getParameter("comment");
		Object o = null;
		Object o1 = null;

		HttpSession session = req.getSession();

		synchronized (session) {
			o = session.getAttribute(StringConstants.AZURE_USER);
			o1 = session.getAttribute("welcomePage");
		}

		if (Util.notNull(webKey) && o != null && o1 != null
				&& Util.notNull(comm)) {
			AzureUser u = (AzureUser) o;
			WelcomePageBean wpb = (WelcomePageBean) o1;

			Key key = KeyFactory.stringToKey(webKey);

			Entity e = GeneralController.findByKey(key);
			if (e != null) {
				Discussion d = EntityConverter.entityToDiscussion(e);
				List<Key> subs = d.getSubscribers();
				if (subs == null) {
					subs = new ArrayList<>();
				}

				subs.add(KeyFactory.createKey(AzureUser.class.getSimpleName(),
						u.getUserID()));

				Discussion comment = new Discussion();

				comment.setOwner(KeyFactory.createKey(
						AzureUser.class.getSimpleName(), u.getUserID()));
				comment.setDateCreated(new Date());
				comment.setParent(d.getId());
				comment.setBody(new Text(comm));

				GeneralController.create(EntityConverter.discussionToEntity(d),
						EntityConverter.discussionToEntity(comment));
				
				//session
				
				List<DiscussionBean> dbs = wpb.getPosts();
				for(DiscussionBean db : dbs) {
					if(db.getWebkey().equals(webKey)){
						db.setComments(db.getComments()+1);
						break;
					}
				}
				
				wpb.setPosts(dbs);
				
				synchronized (session) {
					session.setAttribute("welcomePage", wpb);
				}

			}

		}
	}

}
