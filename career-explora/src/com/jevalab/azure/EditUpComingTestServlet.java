package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.UpComingTest;
import com.jevalab.azure.persistence.UpComingTestJpaController;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.RollbackFailureException;

public class EditUpComingTestServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		AzureUser user = null;
		try {
			user = (AzureUser)req.getSession(false).getAttribute("azureUser");
			String testName = req.getParameter("tName");
			String testDate = req.getParameter("tDate");
			String prepDate = req.getParameter("pDate");
			String oldTName  = req.getParameter("oTName");
			
			List<UpComingTest> ucts = user.getUpComingTests();
			for(int i=0; i < ucts.size(); i++) {
				if(ucts.get(i).getTestName().trim().equalsIgnoreCase(oldTName.trim())) {
					ucts.remove(i);
					UpComingTestJpaController c1 = new UpComingTestJpaController();
					UpComingTest tt = c1.findUpComingTest(oldTName);
					if(tt != null) {
						c1.destroy(tt.getKey());
					}
					UpComingTest upt = new UpComingTest(testName, testDate, prepDate, null);
					ucts.add(upt);
					user.setUpComingTests(ucts);
					UserJpaController cont =  new UserJpaController();
					cont.create(user, null);
					
					resp.setContentType("application/json");
					List<ProcessedUpComingTest> list = new ArrayList<>();
					ProcessedUpComingTest puct = new ProcessedUpComingTest(testName, testDate, prepDate, null);
					list.add(puct);

					String json = null;
					json = new Gson().toJson(list).toString();
					System.out.println(json);
					resp.getWriter().write(json);
					break;
				}
			}
		}catch(NullPointerException npe) {
			resp.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT,
					"Your session is invalid or has expired. Please Login again.");
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
