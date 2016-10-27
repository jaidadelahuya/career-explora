/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.jevalab.azure.cbt;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.CbtRecord;
import com.jevalab.azure.persistence.CbtRecordJpaController;
import com.jevalab.azure.persistence.EnglishCategory;
import com.jevalab.azure.persistence.EnglishCategoryJpaController;
import com.jevalab.azure.persistence.Question;
import com.jevalab.azure.persistence.QuestionJpaController;
import com.jevalab.helper.classes.Util;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetQuestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String subject = req.getParameter("subject");
		String questionNumber = req.getParameter("question");
		String time = req.getParameter("time");
		String passMark = req.getParameter("pass-mark");

		resp.setContentType("application/json");
		if (!(Util.notNull(new String[] { subject }))) {
			resp.sendError(400, new Gson().toJson("Choose a subject"));
			return;
		}
		if (!(Util.notNull(new String[] { questionNumber }))) {
			resp.sendError(400, new Gson().toJson("Enter number of questions"));
			return;
		}
		if (!(Util.notNull(new String[] { time }))) {
			resp.sendError(400, new Gson().toJson("Enter time in minutes"));
			return;
		}
		if (!(Util.notNull(new String[] { passMark }))) {
			passMark = "50";
		}

		HttpSession session = req.getSession();
		AzureUser user = null;
		if (!(session.isNew())) {
			Object o = req.getSession(false).getAttribute("azureUser");
			if (o != null) {
				user = (AzureUser) o;

				int qn = 0;
				try {
					qn = Integer.parseInt(questionNumber);
				} catch (NullPointerException e) {
					resp.sendError(400,
							"Enter a value for the number of questions");
				} catch (NumberFormatException e) {
					resp.sendError(400,
							"The server cannot parse the number of questions you entered");
				}

				QuestionJpaController cont = new QuestionJpaController();
				List ids = cont.findQuestionIds(subject);
				MemcacheService mc = MemcacheServiceFactory
						.getMemcacheService(subject);
				Map qs = mc.getAll(ids);

				List<Question> p = null;
				if ((qs == null) || (qs.isEmpty())) {
					p = cont.findQuestionEntities(subject);

					for (Question qu : p) {
						mc.put(qu.getId(), qu);
					}
				} else {
					Set<Long> keys = qs.keySet();

					Question question = null;

					p = new ArrayList<>();
					for (Long l : keys) {
						question = (Question) qs.get(l);
						p.add(question);
					}

				}

				Set<Question> q = new HashSet();

				Random r = new Random();
				int x = 0;
				while (q.size() < qn) {
					x = r.nextInt(qn);
					if (q.size() < qn)
						q.add((Question) p.get(x));
					else if (q.size() == qn) {
						break;
					}
				}

				CbtRecordJpaController cont1 = new CbtRecordJpaController();
				CbtRecord rec = new CbtRecord(user.getUserID());
				Key key = rec.getKey();
				rec = cont1.findCbtRecord(key);
				JsonQuestion jq = null;

				if (subject.equalsIgnoreCase("English")) {
					List<Question> list = new ArrayList<>();
					List<JsonEnglishCategory> jCat = new ArrayList<>();
					EnglishCategoryJpaController c1 = new EnglishCategoryJpaController();
					List<EnglishCategory> cList = c1.findEnglishCategoryEntities();

					Collections.shuffle(cList);

					for (EnglishCategory e : cList) {
						for (Question qt : q) {
							if (e.getCategoryName()
									.trim()
									.equalsIgnoreCase(
											qt.getCategoryName().trim())) {
								list.add(qt);
							}
						}

						jCat.add(new JsonEnglishCategory(e.getInstruction(),
								list));
						list = new ArrayList<>();
					}
					jq = new JsonQuestion(rec, jCat);
				} else {
					jq = new JsonQuestion(rec, q);
				}

				String json = "{}";
				json = new Gson().toJson(jq).toString();

				resp.setContentType("application/json");
				System.out.println(json);
				resp.getWriter().write(json);
			}
		} else {
			req.getRequestDispatcher("/session/invalid").forward(req, resp);
		}
	}
}