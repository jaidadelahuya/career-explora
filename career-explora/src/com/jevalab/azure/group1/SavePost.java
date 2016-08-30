package com.jevalab.azure.group1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.Discussion;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.helper.classes.EntityConverter;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class SavePost extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8406644954146422900L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Object o = null;
		AzureUser au = null;
		synchronized (session) {
			o = session.getAttribute("postImage");
			au = (AzureUser) session.getAttribute(StringConstants.AZURE_USER);
		}
		String post = req.getParameter("post");
		String tgs = req.getParameter("tags");
		BlobKey key = null;
		if (o != null) {
			key = (BlobKey) o;
		}
		if (Util.notNull(post) || key != null) {
			Discussion d = new Discussion();
			if (Util.notNull(post)) {
				d.setBody(new Text(post));
			}
			d.setDateCreated(new Date());
			d.setFormat("1");
			d.setImage(key);
			d.setOwner(KeyFactory.createKey(AzureUser.class.getSimpleName(),
					au.getUserID()));
			List<String> tags = new ArrayList<>();
			tags.add(au.getsClass());
			tags.addAll(au.getAreaOfInterest());
			if(tgs != null) {
				tags.addAll(Arrays.asList(tgs.split(",")));
			}
			d.setTags(tags);
			GeneralController.create(EntityConverter.discussionToEntity(d));
		}

	}

}
