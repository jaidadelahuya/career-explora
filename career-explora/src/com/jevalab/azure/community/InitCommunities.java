/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.community;

import com.jevalab.helper.classes.MainNav;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InitCommunities extends HttpServlet {
	private static final long serialVersionUID = -7678836856205314692L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		synchronized (session) {
			session.removeAttribute("communitiesError");
			Object o = session.getAttribute("mainNav");
			if (o != null) {
				MainNav mn = (MainNav) o;
				mn.setCommunities(Boolean.valueOf(true));
				session.setAttribute("mainNav", mn);
			}

		}

		req.getRequestDispatcher("/WEB-INF/communities/index.jsp").include(req,
				resp);
	}
}