package com.jevalab.azure;

import java.io.IOException;
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
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.exceptions.InvalidLoginException;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.WelcomePageBean;

public class UploadServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		if (session.isNew()) {
			throw new InvalidLoginException();
		} else {

			String type = req.getParameter("type");
			BlobstoreService bss = BlobstoreServiceFactory
					.getBlobstoreService();
			Map<String, List<BlobKey>> map = bss.getUploads(req);
			List<BlobKey> keys = map.get("picture");
			BlobKey key = null;
			if (keys != null && !keys.isEmpty()) {
				key = keys.get(0);
				Object o = null;
				Object o1 = null;
				AzureUser user = null;
				WelcomePageBean wpb = null;
				synchronized (session) {
					o = session.getAttribute(StringConstants.AZURE_USER);
					o1 = session.getAttribute(StringConstants.WELCOME_PAGE);
				}

				if (o == null) {
					throw new InvalidLoginException();
				} else {
					if (o instanceof AzureUser & o1 instanceof WelcomePageBean) {
						user = (AzureUser) o;
						wpb = (WelcomePageBean) o1;
						ServingUrlOptions suo = ServingUrlOptions.Builder
								.withBlobKey(key);
						ImagesService is = ImagesServiceFactory
								.getImagesService();
						String url = is.getServingUrl(suo);
						resp.getWriter().write(url);
						if("cover".equalsIgnoreCase(type)) {
							user.setCover(url);
						} else {
							user.setPicture(url);
						}
						
						synchronized (session) {
							session.setAttribute(StringConstants.AZURE_USER,
									user);
							session.setAttribute(StringConstants.WELCOME_PAGE,
									wpb);
						}
					}
				}

			} else {
				//error
			}

		}

	}
}
