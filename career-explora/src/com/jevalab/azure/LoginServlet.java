
package com.jevalab.azure;

import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.helper.classes.LoginHelper;
import com.jevalab.helper.classes.MainNav;
import com.jevalab.helper.classes.PasswordRecovery;
import com.jevalab.helper.classes.RegistrationForm;
import com.jevalab.helper.classes.WelcomePageBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();

		AzureUser user = null;
		synchronized (session) {
			user = (AzureUser) session.getAttribute("azureUser");
		}

		List<String> ais = user.getAreaOfInterest();
		if (ais == null) {
			resp.sendRedirect(resp.encodeRedirectURL("/azure/interest/select"));
			return;
		}
		WelcomePageBean wpb = LoginHelper.initWelcomePageBean(user);

		MainNav mn = new MainNav();
		mn.setHome(Boolean.valueOf(true));
		RegistrationForm rf = null;
		synchronized (session) {
			session.setAttribute("mainNav", mn);
			session.setAttribute("welcomePage", wpb);
			rf = (RegistrationForm) session.getAttribute("registrationForm");
		}

		resp.sendRedirect("/azure/welcome");

		PasswordRecovery pr = null;
		if (rf != null) {
			pr = rf.getPasswordRecovery();
		}
		user = LoginHelper.persistUser(user, pr);
	}
}