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

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class SaveTalentsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		if (!session.isNew()) {
			String talents = req.getParameter("talents");
			String testDate = req.getParameter("testDate");
			List<String> tals = Util.asList(talents);
			Map<String, String> mp = Util.getTalentMap(tals);
			
			 Util.updateTalent(session, StringConstants.TALENT_HUNT, testDate, mp);
			

		}

	}

}
