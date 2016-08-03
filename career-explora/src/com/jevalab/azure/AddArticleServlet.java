package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
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
import com.google.appengine.api.datastore.Link;
import com.google.appengine.api.datastore.Text;
import com.jevalab.azure.persistence.Article;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.helper.classes.EntityConverter;
import com.jevalab.helper.classes.Util;

public class AddArticleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3819751613515929126L;

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

		String title = req.getParameter("title");
		String link = req.getParameter("link");
		String bodystr = req.getParameter("post");
		String category = req.getParameter("category");
		String code = req.getParameter("embedded-code");

		HttpSession session = req.getSession();

		if (!Util.notNull(title)) {
			synchronized (session) {
				session.setAttribute("articleError", "Enter Title");
				return;

			}

		}

		if (!Util.notNull(bodystr)) {
			synchronized (session) {
				session.setAttribute("articleError", "Enter Post");
				return;
			}

		}

		if (Util.notNull(category)) {
			category = "100";

		}

		Text body = new Text(bodystr);
		Date date = new Date();
		Article article = new Article();
		article.setTitle(title);
		article.setBody(body);
		article.setDate(date);
		article.setCategory(category);
		article.setImageKey(blobKey);
		if(Util.notNull(code)){
			article.setVideo(new Text(code));
		}
		if(Util.notNull(link)){
			article.setLink(new Link(link));
		}
		
		Entity e = EntityConverter.ArticleToEntity(article);
		GeneralController.create(e);

	}
}
