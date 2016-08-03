package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.jevalab.azure.persistence.Collection;
import com.jevalab.azure.persistence.Community;
import com.jevalab.azure.persistence.Discussion;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.azure.persistence.Unit;
import com.jevalab.helper.classes.EntityConverter;
import com.jevalab.helper.classes.Util;

public class SaveDiscussion extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6667009347236729262L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		String group = req.getParameter("group-name");
		String unitKey = req.getParameter("unit-name");

		if (Util.notNull(group)) {
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

			String title = req.getParameter("title");
			String tags = req.getParameter("tags");
			String format = req.getParameter("format");
			String body = req.getParameter("body");

			if (!Util.notNull(title)) {
				synchronized (session) {
					session.removeAttribute("formSuccess");
					session.setAttribute("formError",
							"Enter a title for the discussion");
				}
				resp.sendRedirect(resp
						.encodeRedirectURL("/ca/admin/discussion/form/new"));
				return;
			}
			if (!Util.notNull(tags)) {
				synchronized (session) {
					session.removeAttribute("formSuccess");
					session.setAttribute("formError",
							"Enter at least a tag for the discussion");
				}
				resp.sendRedirect(resp
						.encodeRedirectURL("/ca/admin/discussion/form/new"));
				return;
			}
			if (!Util.notNull(format)) {
				synchronized (session) {
					session.removeAttribute("formSuccess");
					session.setAttribute("formError",
							"Se;ect a format for the discussion");
				}
				resp.sendRedirect(resp
						.encodeRedirectURL("/ca/admin/discussion/form/new"));
				return;
			}
			if (!Util.notNull(body)) {
				synchronized (session) {
					session.removeAttribute("formSuccess");
					session.setAttribute("formError",
							"Enter the body of the discussion");
				}
				resp.sendRedirect(resp
						.encodeRedirectURL("/ca/admin/discussion/form/new"));
				return;
			}

			Discussion d = new Discussion();
			d.setDateCreated(new Date());
			d.setTitle(title);
			String[] tgs = tags.split(";");
			d.setTags(Arrays.asList(tgs));
			d.setBody(new Text(body));
			d.setFormat(format);
			d.setImage(blobKey);

			Key k1 = KeyFactory.stringToKey(group);
			Object o = Util.getGroupFromCache(k1);
			Entity e = null;
			Entity e1 = null;
			if (o instanceof Collection) {
				Collection c = (Collection) o;
				List<Key> keys = c.getDiscussions();
				keys.add(d.getId());
				d.setCollection(c.getId());
				e = EntityConverter.discussionToEntity(d);
				e1 = EntityConverter.collectionToEntity(c);
			} else if (o instanceof Community) {
				Community c = (Community) o;
				if (Util.notNull(unitKey)) {
					Key key = KeyFactory.stringToKey(unitKey);
					o = null;
					synchronized (session) {
						o = session.getAttribute("caUnits");
					}

					if (o != null) {
						List<Unit> l = (List<Unit>) o;
						Unit u = null;
						for (Unit un : l) {
							if (un.getId().equals(key)) {
								u = un;
							}
						}
						List<Key> keys = u.getDiscussions();
						if (keys == null) {
							keys = new ArrayList<>();
						}
						keys.add(d.getId());
						u.setDiscussions(keys);
						d.setUnit(u.getId());
						d.setCollection(c.getId());
						
						e = EntityConverter.unitToEntity(u);
						e1 = EntityConverter.discussionToEntity(d);


					} else {
						synchronized (session) {
							session.removeAttribute("formSuccess");
							session.setAttribute("formError",
									"A fatal error has occurred");
						}
						resp.sendRedirect(resp
								.encodeRedirectURL("/ca/admin/discussion/form/new"));

					}
					
					GeneralController.createWithCrossGroup(e,e1);
					
					synchronized (session) {
						session.removeAttribute("formError");
						session.setAttribute("formSuccess",
								"Discussion created successfully");
					}

					resp.sendRedirect(resp
							.encodeRedirectURL("/ca/admin/discussion/form/new"));
					return;
				}
			}
		}

	}

}
