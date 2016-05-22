package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.UpComingTest;
import com.jevalab.azure.persistence.UpComingTestTopic;
import com.jevalab.helper.classes.LoginHelper;
import com.jevalab.helper.classes.StringConstants;

public class SaveUpComingTestServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();

		if (!session.isNew()) {
			AzureUser user = null;
			String testName = req.getParameter("test-name");
			String testDate = req.getParameter("test-date");
			String pd = req.getParameter("prep-deadline");
			String tps = req.getParameter("topics");

			Object o = session.getAttribute(StringConstants.AZURE_USER);
			if (o != null) {
				user = (AzureUser) o;

				List<UpComingTestTopic> lists = LoginHelper
						.asUpComingTestTopicsList(tps);
				UpComingTest uct = new UpComingTest(testName, testDate, pd,
						lists);
				user.getUpComingTests().add(uct);
				List<UpComingTest> lucts = new ArrayList<>();
				lucts.add(uct);
				List<ProcessedUpComingTest> newEntry = LoginHelper.getUpcomingTest(lucts);
				Object o1 = null;
				synchronized (session) {
					o1 = session.getAttribute(StringConstants.UPCOMING_TESTS);
				}
				List<ProcessedUpComingTest> pucts = null;
				if (o1 != null) {
					pucts = (List<ProcessedUpComingTest>) o1;
					pucts.addAll(0, newEntry);
					
					Map map = new HashMap(); 
					map.put("uct", newEntry.get(0));
					map.put("length", pucts.size());
					
					resp.setContentType("application/json");
					String json = null;
					json = new Gson().toJson(map).toString();
					System.out.println(json);
					resp.getWriter().write(json);
				}
			}

		}

	}
}
