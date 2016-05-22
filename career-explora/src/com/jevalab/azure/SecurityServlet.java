package com.jevalab.azure;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.exceptions.InvalidLoginException;
import com.jevalab.helper.classes.LoginHelper;
import com.jevalab.helper.classes.StringConstants;

public class SecurityServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String uri = req.getRequestURI();
		uri = uri.substring(uri.lastIndexOf('/')+1);
		
		switch (uri) {
		case "fbook" : RequestDispatcher rd = req.getRequestDispatcher("/facebookhandler");
		rd.forward(req, resp);
		break;
		case "login" : rd = req.getRequestDispatcher("/login");
		rd.forward(req, resp);
		break;
		case "logout" : rd = req.getRequestDispatcher("/logout");
		rd.forward(req, resp);
		break;
		default : HttpSession session = req.getSession();
			Object o = null;
			AzureUser user = null;
			synchronized (session) {
				o = session.getAttribute(StringConstants.AZURE_USER);
			}
			if(o==null) {
				req.setAttribute(StringConstants.NOT_LOGGED_IN, true);
				throw new InvalidLoginException();
	
			} else {
				user = (AzureUser) o;
				if(user.isAuthorized()) {
					rd = req.getRequestDispatcher("/"+uri);
				} else {
					rd = req.getRequestDispatcher("/authorization");
				}
			}
			
		rd.forward(req, resp);
		}

	}

}
