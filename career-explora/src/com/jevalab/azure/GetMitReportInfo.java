package com.jevalab.azure;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.MultipleIntelligenceTestQuestion;
import com.jevalab.helper.classes.MitReport;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class GetMitReportInfo extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		MitReport mr = null;
		Object o = null;
		synchronized (session) {
			o = session.getAttribute(StringConstants.MIT_REPORT);
		}
		
		if(o != null) {
			
			mr = (MitReport) o;
			Map<MultipleIntelligenceTestQuestion,String> vsl = mr.getVsmit();
			Map<MultipleIntelligenceTestQuestion,String> sl = mr.getSmit();
			Map<MultipleIntelligenceTestQuestion,String> fsl = mr.getFsmit();
			
			Map<String,String> vsm = Util.getMitNames(vsl);
			Map<String,String> sm = Util.getMitNames(sl);
			Map<String,String> fsm = Util.getMitNames(fsl);
			
			Map<String,Map<String,String>> respMap = new HashMap<>();
			respMap.put("vs", vsm);
			respMap.put("s", sm);
			respMap.put("fs", fsm);
			
			String msg = Util.toJsonString(respMap);
			
			resp.setContentType("application/json");
			resp.getWriter().write(msg);
			
			
		}
	}
}
