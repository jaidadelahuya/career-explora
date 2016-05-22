package com.jevalab.azure;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.Record;
import com.jevalab.azure.persistence.RecordJpaController;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.exceptions.RollbackFailureException;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.UserProfile;
import com.jevalab.helper.classes.Util;

public class SaveMitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		if (!session.isNew()) {
			String types = req.getParameter("types");
			String testDate = req.getParameter("testDate");
			boolean updated = Util.updateMITS(session,
					StringConstants.MULTIPLE_INTELLIGENCE_TEST, testDate,
					new Text(types));

			UserProfile up = null;
			if (updated) {

				up = Util.getUserProfileFromSession(session);
				List<String> list = up.getMits();
				Map<String, Object> map = new HashMap<>();

				if (list.size() > 6) {
					list = up.getMits().subList(0, 6);
					map.put("hasMore", true);
				} else {
					map.put("hasMore", false);
				}

				map.put("list", list);
	
			
				if(up.isCurrentUser()) {
					Util.useJSON(map, resp);
				}
				

			} else {
				resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,
						"We could not save your test on the server. Please try again.");
			}

		}

	}
}
