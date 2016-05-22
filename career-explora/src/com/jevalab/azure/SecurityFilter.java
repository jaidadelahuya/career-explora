package com.jevalab.azure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.UserJpaController;
import com.jevalab.helper.classes.StringConstants;

public class SecurityFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res = (HttpServletResponse) arg1;

		String id = req.getParameter(StringConstants.NAME);
		String accessToken = req.getParameter(StringConstants.ACCESS);
		String picture = req.getParameter(StringConstants.PICTURE);
		String cover = req.getParameter(StringConstants.COVER);
		Boolean fromAuthorization = new Boolean(req.getParameter(StringConstants.AUTHORIZATION));

		//do validation first. create validator using strategy desin pattern
		/*if(fromAuthorization) {
			
		} else {
			
			if(req.getMethod().equalsIgnoreCase(StringConstants.GET) || id.isEmpty() || id == null || accessToken.isEmpty() || accessToken == null) {
				res.sendRedirect(StringConstants.INDEX_PAGE);
				return;
			}
		}*/
		
		AzureUser user = null;
		HttpSession session = req.getSession();
		
		UserJpaController cont = new UserJpaController();

		if (session.isNew()) {
			
			user = cont.findUser(id);
			if(user == null) {
				user = new AzureUser(id);
			}
			user.setPicture(picture);
			user.setCover(cover);
			user.setAccessToken(accessToken);
			session.setAttribute(StringConstants.AZURE_USER, user);
			redirectUser(user, res, req, chain);
			
		} else {
			
			Object object = session.getAttribute(StringConstants.AZURE_USER);
			if (object != null) {
				
				user = (AzureUser) object;
			} else {
				//send back to login
				user = cont.findUser(id);
				if(user == null) {
					user = new AzureUser(id);
					user.setAccessToken(accessToken);
					session.setAttribute(StringConstants.AZURE_USER, user);
				}
			}
			redirectUser(user, res, req, chain);
		}
	}

	private void redirectUser(AzureUser user, HttpServletResponse res,
			HttpServletRequest req, FilterChain chain) throws IOException,
			ServletException {
		
		if (user.isAuthorized()) {
			
			chain.doFilter(req, res);
			
		} else {
			
			req.getRequestDispatcher("WEB-INF/authorization.jsp").forward(req, res);
			
				
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		

	}

}
