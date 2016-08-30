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

public class ServeNewImageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5483684813553793914L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BlobstoreService bss = BlobstoreServiceFactory.getBlobstoreService();
		Map<String, List<BlobKey>> map = bss.getUploads(req);
		List<BlobKey> list = map.get("image");
		String url = "";
		if(list!=null && !list.isEmpty()) {
			BlobKey key = list.get(0);
			ImagesService is = ImagesServiceFactory.getImagesService();
			url = is.getServingUrl(ServingUrlOptions.Builder.withBlobKey(key));
			
			HttpSession session = req.getSession();
			synchronized (session) {
				session.setAttribute("postImage", key);
			}
		}
		

		resp.getWriter().write(url);
	}

}
