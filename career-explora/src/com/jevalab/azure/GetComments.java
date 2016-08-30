package com.jevalab.azure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.helper.classes.Util;

public class GetComments extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -902955075654064655L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String webkey = req.getParameter("webkey");
		if(Util.notNull(webkey)) {
			Key key = KeyFactory.stringToKey(webkey);
			//List<Entity> ents = GeneralController.getComments(key);
		}
	}

}
