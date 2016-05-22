package com.jevalab.azure;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.helper.classes.StringConstants;

public class PaymentSuccess extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.isNew()) {
			resp.sendRedirect("/payment-failure.html");
		} else {
			Object o = null;
			AzureUser user = null;
			o = session.getAttribute(StringConstants.AZURE_USER);
			if (o == null) {
				resp.sendRedirect("/payment-failure.html");
			} else {
				user = (AzureUser) o;
				if (user.isAuthorized()) {
					req.setAttribute(StringConstants.FROM_PAYMENT, true);
					RequestDispatcher rd = req
							.getRequestDispatcher("/login-page");
					rd.forward(req, resp);

				} else {
					RequestDispatcher rd = req
							.getRequestDispatcher("/payment-pending");
					rd.forward(req, resp);
				}
			}
		}
	}

}
