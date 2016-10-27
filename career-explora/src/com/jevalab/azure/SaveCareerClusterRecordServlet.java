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

import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class SaveCareerClusterRecordServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();

		if (!session.isNew()) {
			String clusters = req.getParameter("clusters");
			String testDate = req.getParameter("testDate");
			List<String> vals = Util.asList(clusters);
			Map<String, String> mp = Util.getCareerMap(vals);

			Util.updateCareerCluster(session,
					StringConstants.CAREER_CLUSTERS, testDate, mp);

		} else {
			resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,
					"We could not save your test on the server. Please try again.");
		}
	}

}
