package com.jevalab.azure;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.KeyFactory;
import com.jevalab.azure.persistence.Community;
import com.jevalab.azure.persistence.GeneralController;

public class GetCACommunities extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -798715903840595405L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Community> comms = GeneralController.findCACommunities();
		Map<String, String> map = new HashMap<String, String>();
		for(Community c: comms) {
			map.put(KeyFactory.keyToString(c.getId()), c.getName());
		}
		HttpSession session = req.getSession();
		
		synchronized (session) {
			session.setAttribute("communityMap", map);
			session.setAttribute("caComms", comms);
		}
		
		RequestDispatcher rd = req.getRequestDispatcher("/ca/admin/unit/form/new");
		rd.include(req, resp);
	
	}

}
