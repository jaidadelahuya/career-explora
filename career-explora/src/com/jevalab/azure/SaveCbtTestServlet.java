package com.jevalab.azure;

import java.io.IOException;

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
import com.jevalab.helper.classes.Util;
import com.jevalab.helper.classes.WelcomePageBean;

public class SaveCbtTestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String TEST_NAME = "CBT";
	private final static String DEFAULT_VENDOR = "GENERAL";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();

		if (!session.isNew()) {
			String data = req.getParameter("data");
			String testDate = req.getParameter("testDate");
			String subjectName = req.getParameter("subjectName");

			boolean updated = Util.updateLastTestTaken(session, data,
					TEST_NAME, testDate, subjectName, DEFAULT_VENDOR);

			if (!updated) {
				resp.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,
						"We could not save your test on the server. Please try again.");
			}

		}

	}
}
