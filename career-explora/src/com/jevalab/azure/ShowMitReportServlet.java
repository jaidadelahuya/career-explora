package com.jevalab.azure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.exceptions.InvalidLoginException;
import com.jevalab.helper.classes.MitReport;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class ShowMitReportServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		AzureUser user = null;
		HttpSession session = req.getSession();
		Object o = null;
		List<String> mits = null;
		synchronized (session) {
			o = session.getAttribute(StringConstants.AZURE_USER);
		}
		
		if(o == null){
			throw new InvalidLoginException();
		} else {
			user = (AzureUser) o;
			MitReport report = Util.getMitReport(user);
			synchronized (session) {
				session.setAttribute(StringConstants.MIT_REPORT, report);
			}
			resp.setStatus(HttpServletResponse.SC_OK);
		}
		
	}
}
