package com.jevalab.azure;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;
import com.jevalab.exceptions.InvalidLoginException;
import com.jevalab.helper.classes.Util;


public class UploadUrlServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CALlBACK = "/upload";
	@Override
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String url = req.getParameter("url");
		if(!Util.notNull(url)){
			url = CALlBACK;
		}
		
		if(session.isNew()) {
			throw new InvalidLoginException();
		} else {
			BlobstoreService bss = BlobstoreServiceFactory.getBlobstoreService();
			UploadOptions upo = UploadOptions.Builder.withMaxUploadSizeBytesPerBlob(2*1024*1024).maxUploadSizeBytes(2*1024*1024);
			String upLoadUrl = bss.createUploadUrl(url,upo);
			
			resp.setContentType("text/html");
			req.setAttribute("uploadUrl", upLoadUrl);
			
			resp.getWriter().write(upLoadUrl);
		}
	
		
		
	}
}
