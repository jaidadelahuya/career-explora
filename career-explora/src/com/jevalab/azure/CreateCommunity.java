package com.jevalab.azure;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Text;
import com.jevalab.azure.persistence.Collection;
import com.jevalab.azure.persistence.Community;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.helper.classes.EntityConverter;
import com.jevalab.helper.classes.Util;

public class CreateCommunity extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8705651875810534291L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		Map<String, List<BlobKey>> blobFields = blobstoreService
				.getUploads(req);
		List<BlobKey> blobKeys = blobFields.get("image");
		BlobKey blobKey = null;
		if (blobKeys != null && !blobKeys.isEmpty()) {
			// We're only expecting one, so take the first one.
			blobKey = blobKeys.get(0);
		}
		
		String name = req.getParameter("name");
		String tags = req.getParameter("tags");
		String shortDesc = req.getParameter("short-desc");
		String longDesc = req.getParameter("long-desc");
		String group = req.getParameter("group");
		
		HttpSession session = req.getSession();
		if(!Util.notNull(name)) {
			synchronized (session) {
				session.removeAttribute("formSuccess");
				session.setAttribute("formError", "Enter a name for the community or collection");
			}
			resp.sendRedirect(resp.encodeRedirectURL("/ca/admin/community/form/new"));
			return;
		}
		if(!Util.notNull(shortDesc)) {
			synchronized (session) {
				session.removeAttribute("formSuccess");
				session.setAttribute("formError", "Enter a short description for the community or collection");
			}
			resp.sendRedirect(resp.encodeRedirectURL("/ca/admin/community/form/new"));
			return;
		}
		if(!Util.notNull(longDesc)) {
			synchronized (session) {
				session.removeAttribute("formSuccess");
				session.setAttribute("formError", "Enter a long description for the community or collection");
			}
			resp.sendRedirect(resp.encodeRedirectURL("/ca/admin/community/form/new"));
			return;
		}
		if(!Util.notNull(tags)) {
			synchronized (session) {
				session.removeAttribute("formSuccess");
				session.setAttribute("formError", "Enter at least a tag for the community or collection");
			}
			resp.sendRedirect(resp.encodeRedirectURL("/ca/admin/community/form/new"));
			return;
		}
		if(!Util.notNull(group)) {
			synchronized (session) {
				session.removeAttribute("formSuccess");
				session.setAttribute("formError", "Select a group");
			}
			resp.sendRedirect(resp.encodeRedirectURL("/ca/admin/community/form/new"));
			return;
		}
		
		if(group.equalsIgnoreCase("community")) {
			Community c = new Community();
			c.setBackgroundImage(blobKey);
			c.setDateCreated(new Date());
			c.setJoinMode(JoinMode.NO_REQUEST.name());
			c.setLongDesc(new Text(longDesc));
			c.setName(name);
			c.setShortDesc(new Text(shortDesc));
			c.setTags(Arrays.asList(tags.split(",")));
			c.setVisible(true);
			Entity e = EntityConverter.communityToEntity(c);
			GeneralController.create(e);
		}else if(group.equalsIgnoreCase("collection")) {
			Collection c = new Collection();
			c.setBackgroundImage(blobKey);
			c.setDateCreated(new Date());
			c.setLongDesc(new Text(longDesc));
			c.setName(name);
			c.setShortDesc(new Text(shortDesc));
			c.setTags(Arrays.asList(tags.split(",")));
			c.setVisible(true);
			Entity e = EntityConverter.collectionToEntity(c);
			GeneralController.create(e);
		}
		
		synchronized (session) {
			session.removeAttribute("formError");
			session.setAttribute("formSuccess", "Community/ Collection created successfully");
		}
		
		resp.sendRedirect(resp.encodeRedirectURL("/ca/admin/community/form/new"));
		return;
	}

}
