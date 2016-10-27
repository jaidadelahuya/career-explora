/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.group1;

import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.helper.classes.DiscussionBean;
import com.jevalab.helper.classes.Util;
import com.jevalab.helper.classes.WelcomePageBean;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMoreDiscussions extends HttpServlet {
	private static final long serialVersionUID = 5135295480814458584L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Object o = null;
		Object o1 = null;

		HttpSession session = req.getSession();

		synchronized (session) {
			o = session.getAttribute("welcomePage");
			o1 = session.getAttribute("azureUser");
		}

		if ((o != null) && (o1 != null)) {
			WelcomePageBean wpb = (WelcomePageBean) o;
			AzureUser u = (AzureUser) o1;
			int offset = wpb.getOffset().intValue();
			Map<String,Object> map = Util.getPreferredPosts(u, offset);
			wpb.setOffset((Integer) map.get("offset"));
			List<DiscussionBean> list = (List<DiscussionBean>) map.get("post");
			wpb.getPosts().addAll(list);
			synchronized (session) {
				session.setAttribute("welcomePage", wpb);
			}
			resp.setContentType("application/json");
			resp.getWriter().write(new Gson().toJson(list));
		} else {
			req.getRequestDispatcher("/session/invalid").forward(req, resp);
		}
	}
}