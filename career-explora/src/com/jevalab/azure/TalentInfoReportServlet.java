package com.jevalab.azure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.TalentInfo;
import com.jevalab.helper.classes.TalentReport;
import com.jevalab.helper.classes.Util;

public class TalentInfoReportServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		Object o = null;
		synchronized (session) {
			o= session.getAttribute(StringConstants.TALENT_REPORT);
		}
		if(o!=null) {
			TalentReport tr = (TalentReport) o;
			Map<String,String> map = new HashMap<>();
			map = addToMap(tr.getHeadTalents(),map);
			map = addToMap(tr.getHandTalents(),map);
			map = addToMap(tr.getBodyTalents(),map);
			String res = Util.toJsonString(tr);
			resp.setContentType("application/json");
			resp.getWriter().write(res);
		}
	}

	private Map<String, String> addToMap(Set<TalentInfo> talents,Map<String,String> map) {
		for(TalentInfo ti: talents) {
			map.put(ti.getName(), ti.getValue());
		}
		return map;
	}
}
