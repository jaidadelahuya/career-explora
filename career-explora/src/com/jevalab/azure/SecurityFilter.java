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

		HttpSession session = req.getSession();
		Object o = null;
		synchronized (session) {
			o = session.getAttribute(StringConstants.AZURE_USER);
		}
		if (o == null) {
			if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN,
						"You don't have an active session. <a href='/'>login again</a>");
				return;
			}else {
				res.sendRedirect("/");
			}
		}else {
			AzureUser u = (AzureUser) o;
			if(u.isAuthorized()) {
				chain.doFilter(arg0, arg1);
			} else {
				res.sendRedirect(res.encodeRedirectURL("/authorization"));
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
