package com.jevalab.azure;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.jevalab.azure.persistence.AzureUser;
import com.jevalab.azure.persistence.UpComingTest;
import com.jevalab.azure.persistence.UpComingTestJpaController;
import com.jevalab.exceptions.NonexistentEntityException;
import com.jevalab.exceptions.RollbackFailureException;
import com.jevalab.helper.classes.LoginHelper;
import com.jevalab.helper.classes.StringConstants;
import com.jevalab.helper.classes.UpdateHelperClass;

public class DeleteUpcomingTestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();

		if (!session.isNew()) {
			String keyString = req.getParameter("keyString");
			if (keyString != null) {
				Key key = KeyFactory.stringToKey(keyString);
				Object o = null;
				Object o1 = null;
				synchronized (session) {
					o = session.getAttribute(StringConstants.AZURE_USER);
					o1 = session.getAttribute(StringConstants.UPCOMING_TESTS);
				}

				UpComingTest uct = UpdateHelperClass
						.deleteUpComingTest(key);

				if (o != null) {
					AzureUser user = (AzureUser) o;
					user.getUpComingTests().remove(uct);
				}

				if (o1 != null) {
					ProcessedUpComingTest puct = new ProcessedUpComingTest(
							uct.getTestName(), uct.getTestDate(), null, null);
					List<ProcessedUpComingTest> list = (List<ProcessedUpComingTest>) o1;
					list.remove(puct);
					// to gc
					puct = null;
				}

				// to gc
				uct = null;

			}

		}

	}

}
