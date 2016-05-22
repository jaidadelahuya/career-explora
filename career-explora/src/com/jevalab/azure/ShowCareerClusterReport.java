package com.jevalab.azure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.exceptions.InvalidLoginException;
import com.jevalab.helper.classes.CareerClusterReport;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class ShowCareerClusterReport extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3637446701526909240L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		AzureUser user = null;
		HttpSession session = req.getSession();
		Object o = null;
		synchronized (session) {
			o = session.getAttribute(StringConstants.AZURE_USER);
		}
		if(o == null){
			throw new InvalidLoginException();
		} else {
			user = (AzureUser) o;
			CareerClusterReport report = Util.getCareerClusterReport(user.getUserID());
			synchronized (session) {
				session.setAttribute(StringConstants.CAREER_CLUSTER_REPORT, report);
			}
		
			String data = Util.toJsonString(report);
			resp.setContentType("application/json");
			resp.getWriter().write(data);
		}
		
	}
}
