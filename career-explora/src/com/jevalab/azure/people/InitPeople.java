/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.people;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.helper.classes.MainNav;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class InitPeople extends HttpServlet {
	private static final long serialVersionUID = -2838555889945911714L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String category = req.getParameter("category");
		if(category == null) {
			category = "1";
		}
		AzureUser u = null;
		Object o2 = null;
		synchronized (session) {
			o2 = session.getAttribute(StringConstants.AZURE_USER);
		}
		if (Util.notNull(category) && o2 != null) {
			u = (AzureUser) o2;
			Object o1 = null;
			synchronized (session) {
				session.removeAttribute("peopleError");
				Object o = session.getAttribute("mainNav");
				if (o != null) {
					MainNav mn = (MainNav) o;
					mn.setPeople(Boolean.valueOf(true));
					session.setAttribute("mainNav", mn);
				}
				o1 = session.getAttribute("peoplePageBean");

			}
			
			PeoplePageBean ppb = null;

			if (o1 != null
					&& category.equalsIgnoreCase(((PeoplePageBean) o1)
							.getCategory())) {
				ppb = (PeoplePageBean) o1;
				ppb = Util.getPeoplePageBean(ppb,u);
			}else {
				ppb = Util.getPeoplePageBean(category,u);
			}

			synchronized (session) {
				session.setAttribute("peoplePageBean", ppb);
			}
			req.getRequestDispatcher("/WEB-INF/people/index.jsp").include(req,
					resp);
		}

	}
}