package com.jevalab.azure;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.UsedKey;
import com.jevalab.azure.persistence.UsedKeyJpaController;
import com.jevalab.azure.persistence.UserKey;
import com.jevalab.azure.persistence.UserKeyJpaController;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.RollbackFailureException;
import com.jevalab.helper.classes.StringConstants;

public class TokenVerifyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String key = req.getParameter("key");

		if (key == null || key.isEmpty()) {

			resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Please enter your Authorization Code");
		} else {

			UserKeyJpaController c1 = new UserKeyJpaController();
			UserKey uk = c1.findUser(key.trim());

			if (uk == null) {
				UsedKeyJpaController c2 = new UsedKeyJpaController();
				UsedKey sk = c2.findUser(key.trim());
				if (sk != null) {
					resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
							"The code you entered has been used.");
				} else {
					resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
							"The code you entered has been used or does not exists");
				}

			} else {

				HttpSession session = req.getSession();
				AzureUser user = null;

				if (session.isNew()) {

					resp.sendError(HttpServletResponse.SC_FORBIDDEN,
							"Cannot process your request. Try login again.");
					return;
				} else {

					Object object = null;
					synchronized (session) {
						object = session
								.getAttribute(StringConstants.AZURE_USER);
					}

					if (object != null) {

						user = (AzureUser) object;
					} else {

						resp.sendError(HttpServletResponse.SC_FORBIDDEN,
								"Cannot process your request. Try login again.");
						return;
					}

					user.setAuthorized(true);
					user.setValidity(uk.getValidity());
					user.setSubscriptionDate(new Date());
					user.setNewUser(true);
					synchronized (session) {
						session.setAttribute(StringConstants.AZURE_USER, user);
					}

					try {
						UsedKey usk = new UsedKey(key.trim());
						usk.setValidity(uk.getValidity());
						UsedKeyJpaController c2 = new UsedKeyJpaController();
						c1.destroy(key.trim());
						c2.create(usk);

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

					Map<String, String> map = new HashMap<>();
					map.put("validity", uk.getValidity());
					String json = new Gson().toJson(map).toString();

					resp.setContentType("application/json");
					System.out.println(json);
					resp.getWriter().write(json);

				}

			}
		}

	}
}
