package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jevalab.azure.persistence.Community;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.azure.persistence.Unit;
import com.jevalab.helper.classes.EntityConverter;

public class AddCAUnitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6826356641285134804L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String comKey = req.getParameter("community");
		String unitName = req.getParameter("name");
		
		List<Community> list = null;
		Object o = null;
		HttpSession session = req.getSession();
		synchronized (session) {
			o = session.getAttribute("caComms");
		}
		
		if(o == null) {
			synchronized (session) {
				session.setAttribute("formError", "A fatal error has occured");
				session.removeAttribute("formSuccess");
			}
		}else {
			list= (List<Community>) o;
			Key key = KeyFactory.stringToKey(comKey);
			Community c = null;
			for(Community co : list) {
				if(co.getId().equals(key)) {
					c=co;
					break;
				}
			}
			Unit u = new Unit();
			u.setName(unitName);
			u.setCommunity(c.getId());
			List<Key> keys = c.getUnits();
			if(keys == null) {
				keys = new ArrayList<>();
			}
			keys.add(u.getId());
			c.setUnits(keys);
			Entity e = EntityConverter.unitToEntity(u);
			Entity e1 = EntityConverter.communityToEntity(c);
			GeneralController.createWithCrossGroup(e,e1);
			synchronized (session) {
				session.setAttribute("formSuccess", "Unit saved successfully");
				session.removeAttribute("formError");
			}
		}
		
		resp.sendRedirect(resp.encodeRedirectURL("/ca/admin/unit/form/new"));
	}

}
