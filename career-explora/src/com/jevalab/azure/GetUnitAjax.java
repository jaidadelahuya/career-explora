package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.jevalab.azure.persistence.Community;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.azure.persistence.Unit;
import com.jevalab.helper.classes.EntityConverter;
import com.sun.swing.internal.plaf.synth.resources.synth;

public class GetUnitAjax extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6247819223535587059L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String webkey = req.getParameter("webkey");
		Key k = KeyFactory.stringToKey(webkey);
		
		HttpSession session = req.getSession();
		Object o = null;
		synchronized (session) {
			o = session.getAttribute("caComms");
		}
		if(o != null) {
			List<Community> l = (List<Community>) o;
			Community cm = null;
			for(Community c: l) {
				if(c.getId().equals(k)) {
					cm = c;
					break;
				}
			}
			List<Key> unitKeys = cm.getUnits();
			if(unitKeys == null) {
				return;
			}
			Map<Key, Entity> mp = GeneralController.findByKeys(unitKeys);
			Map<String,String> map = new HashMap<String, String>();
			List<Unit> units = new ArrayList<>();
			Set<Key> ks = mp.keySet();
			List<EmbeddedEntity> ees = new ArrayList<>();
			for(Key kk : ks) {
				units.add(EntityConverter.entityToUnit(mp.get(kk)));
				map.put(KeyFactory.keyToString(kk),(String)mp.get(kk).getProperty("name") );
				
				EmbeddedEntity ee = new EmbeddedEntity();
				ee.setProperty("id", KeyFactory.keyToString(kk));
				ee.setUnindexedProperty("name", (String)mp.get(kk).getProperty("name"));
				ees.add(ee);
			}
			
			synchronized (session) {
				session.setAttribute("caUnits", units);
				session.setAttribute("unitsMap", map);
			}
			
			
			Gson gson = new Gson();
			resp.setContentType("application/json");
			resp.getWriter().write(gson.toJson(ees));
			
			
			
		}
	}

}
