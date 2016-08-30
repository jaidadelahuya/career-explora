package com.jevalab.azure.group1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.Discussion;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.helper.classes.DiscussionBean;
import com.jevalab.helper.classes.EntityConverter;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;
import com.jevalab.helper.classes.WelcomePageBean;

public class AddLike extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5985685432127198226L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String webKey  = req.getParameter("webkey");
		Object o =null;
		Object o1 = null;
		HttpSession session = req.getSession();
		
		synchronized (session) {
			o = session.getAttribute(StringConstants.AZURE_USER);
			o1 = session.getAttribute("welcomePage");
		}
		if(Util.notNull(webKey) && o !=null && o1!=null) {
			AzureUser u =(AzureUser) o;
			Key key = KeyFactory.stringToKey(webKey);
			Entity e = GeneralController.findByKey(key);
			if(e != null) {
				Discussion d = EntityConverter.entityToDiscussion(e);
				List<Key> likers = d.getLikers();
				
				if(likers == null) {
					likers = new ArrayList<>();
				}
				Key userKey = KeyFactory.createKey(AzureUser.class.getSimpleName(), u.getUserID());
				
				if(likers.contains(userKey)) {
					likers.remove(userKey);
				}else {
					likers.add(userKey);
			
				}
				d.setLikers(likers);
				GeneralController.create(EntityConverter.discussionToEntity(d));
				
				//session
				WelcomePageBean wpb = (WelcomePageBean) o1;
				List<DiscussionBean> ds = wpb.getPosts();
				long likes = 0;
				for(DiscussionBean b : ds) {
					if(b.getWebkey().equals(webKey)) {
						if(b.isLiked()) {
							b.setLiked(false);
							b.setLikes(b.getLikes()-1);
						}else {
							b.setLiked(true);
							b.setLikes(b.getLikes()+1);
						}
						likes = b.getLikes();
						break;
					}
				}
				
				wpb.setPosts(ds);
				synchronized (session) {
					session.setAttribute("welcomePage", wpb);
				}
				
				resp.setContentType("application/json");
				resp.getWriter().write(new Gson().toJson(likes));
				
			}
			
		}
	}

}
