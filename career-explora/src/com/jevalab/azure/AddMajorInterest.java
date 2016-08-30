package com.jevalab.azure;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.helper.classes.LoginHelper;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class AddMajorInterest extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3078632727987491724L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String school = req.getParameter("school");
		String sClass = req.getParameter("class");
		String[] interest = req.getParameterValues("interest");
		resp.setContentType("application/json");
		if (!Util.notNull(school)) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
					"Enter the name of your school.");
			return;
		}
		if (!Util.notNull(sClass)) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
					"Which class are you?");
			return;
		}
		if (!Util.notNull(interest)) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
					"Select at least one area of interest.");
			return;
		}

		HttpSession session = req.getSession();

		Object o = null;

		synchronized (session) {
			o = session.getAttribute(StringConstants.AZURE_USER);
		}

		if (o == null) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
					"Your session has expired. Login again.");
		} else {
			String[] ints = interest;
			AzureUser u = (AzureUser) o;
			u.setSchool(school);
			u.setsClass(sClass);
			u.setAreaOfInterest(Arrays.asList(ints));

			synchronized (session) {
				session.setAttribute(StringConstants.AZURE_USER, u);
			}

			String uploadUrl = BlobstoreServiceFactory.getBlobstoreService()
					.createUploadUrl(
							"/azure/interest/profile/picture/upload",
							UploadOptions.Builder
									.withMaxUploadSizeBytes(1 * 1024 * 1024));
			resp.getWriter().write(new Gson().toJson(uploadUrl));

			
			 

		}

	}

}
