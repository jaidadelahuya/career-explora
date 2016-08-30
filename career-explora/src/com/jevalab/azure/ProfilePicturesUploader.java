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
import com.google.appengine.api.blobstore.UploadOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.helper.classes.LoginHelper;
import com.jevalab.helper.classes.StringConstants;

public class ProfilePicturesUploader extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -608690232668691013L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String type = req.getParameter("type");
		BlobstoreService service = BlobstoreServiceFactory
				.getBlobstoreService();
		Map<String, List<BlobKey>> map = service.getUploads(req);
		BlobKey blobKey = null;
		if (map != null) {
			List<BlobKey> list = map.get("image");
			if (list != null) {
				blobKey = list.get(0);
			}else {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No Image was uploaded");
				return;
			}
		}

		Object o = null;
		HttpSession session = req.getSession();
		synchronized (session) {
			o = session.getAttribute(StringConstants.AZURE_USER);
		}
		if (o != null) {
			AzureUser user = (AzureUser) o;
			String url = "";
			if(blobKey!=null) {
				 url = ImagesServiceFactory.getImagesService().getServingUrl(
						ServingUrlOptions.Builder.withBlobKey(blobKey));
			}
			
			String data = "";
			if (type.equals("profile")) {
				user.setPicture(url);
				data = BlobstoreServiceFactory.getBlobstoreService()
						.createUploadUrl(
								"/azure/interest/profile/picture/upload",
								UploadOptions.Builder
										.withMaxUploadSizeBytes(1 * 1024 * 1024));
			} else if (type.equals("cover")) {
				user.setCover(url);
				List<String> ints = user.getAreaOfInterest();
				String sClass = user.getsClass();
				String iStr = "";
				for (String s : ints) {
					iStr += ("&interest=" + s);
				}
				String c = "&class=" + sClass;
				
				String webkey = KeyFactory.createKeyString(
						AzureUser.class.getSimpleName(), user.getUserID());
				data = resp.encodeRedirectURL("/azure/success?id=" + webkey
						+ iStr + c);
				user = LoginHelper.persistUser(user, null);
			}

			synchronized (session) {
				session.setAttribute(StringConstants.AZURE_USER, user);
			}
			resp.setContentType("application/json");
			resp.getWriter().write(new Gson().toJson(data.trim()));
		}

	}

}
