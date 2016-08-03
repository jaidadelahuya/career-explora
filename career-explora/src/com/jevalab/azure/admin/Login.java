package com.jevalab.azure.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.GeneralController;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.Util;

public class Login extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7346051425926561576L;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		HttpSession session = req.getSession();
		if(!Util.notNull(email,password)) {
			
			synchronized (session) {
				session.removeAttribute("formSuccess");
				session.setAttribute("formError", "Invalid Login");
			}
			resp.sendRedirect("/ca/admin/form/login");
		}else {
			AzureUser u = GeneralController.findUserByLogin(email,password);
			if(u == null) {
				synchronized (session) {
					session.removeAttribute("formSuccess");
					session.setAttribute("formError", "Invalid Login");
				}
				resp.sendRedirect("/ca/admin/login");
			}else {
				synchronized (session) {
					session.setAttribute(StringConstants.AZURE_USER, u);
					session.removeAttribute("formError");
				}
				resp.sendRedirect(resp.encodeRedirectURL("/ca/admin/dashboard"));
			}
		}
	}

}
