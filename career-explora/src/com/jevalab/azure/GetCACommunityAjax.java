package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.jevalab.azure.persistence.Community;
import com.jevalab.azure.persistence.GeneralController;

public class GetCACommunityAjax extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1370888793423248931L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Community> comms = GeneralController.findCACommunities();
		Map<String, String> map = new HashMap<String, String>();
		for (Community c : comms) {
			map.put(KeyFactory.keyToString(c.getId()), c.getName());
		}
		HttpSession session = req.getSession();

		synchronized (session) {
			session.setAttribute("communityMap", map);
			session.setAttribute("caComms", comms);
		}
		List<EmbeddedEntity> ees = new ArrayList<>();
		
		Set<String> set = map.keySet();
		for(String s: set) {
			EmbeddedEntity ee = new EmbeddedEntity();
			ee.setProperty("id", s);
			ee.setUnindexedProperty("webkey", map.get(s));
			ees.add(ee);
		}

		Gson gson = new Gson();
		resp.setContentType("application/json");
		resp.getWriter().write(gson.toJson(ees));
	}

}
