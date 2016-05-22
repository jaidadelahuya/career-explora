package com.jevalab.azure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jevalab.azure.persistence.CareerCluster;
import com.jevalab.azure.persistence.CareerClusterJpaController;

public class CareerClusterBoardServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String msg = req.getParameter("test");
		
		CareerClusterJpaController cont = new CareerClusterJpaController();
		List<CareerCluster> ccl = null;
		String clusterList = null;
		if (msg == null) {
			return;
		}else if(msg.equalsIgnoreCase("test-1")) {
			clusterList = "'Agriculture and Natural Resources','Arts, Audio/Video Technology, and Communications','Education and Training','Government and Public Administration','Hospitality and Tourism','Information Technology','Manufacturing','Science, Technology, Engineering & Mathematics'";
			ccl = cont.findClusterEntities(clusterList);
		} else if (msg.equalsIgnoreCase("test-2")) {
			clusterList = "'Architecture and Construction','Business and Administration','Finance','Health Science','Human Services','Law, Public Safety, Corrections & Security','Marketing','Transportation, Distribution, and Logistics'";
			ccl = cont.findClusterEntities(clusterList);
		} else {
			return;
		}
		for(CareerCluster cc : ccl){
			//cc.getPathway();
			cc.getQuestions();
		}
		
		List<CareerClusterQuestion> ccqs = new ArrayList<>();
		/*for(CareerCluster cc : ccl) {
			for(String q: cc.getQuestions()) {
				CareerClusterQuestion ccq = new CareerClusterQuestion(cc.getClusterName(), cc.getDescription().toString(), q);
				ccqs.add(ccq);
			}
		}
		
		Collections.shuffle(ccqs);*/
		String json = null;
		json = new Gson().toJson(ccl).toString();
		resp.setContentType("application/json");
		System.out.println(json);
		resp.getWriter().write(json);
	}
}
