package com.jevalab.azure.books;

import com.jevalab.helper.classes.MainNav;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InitBooks extends HttpServlet {
	private static final long serialVersionUID = 4347137457478814907L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		synchronized (session) {
			session.removeAttribute("booksError");
			Object o = session.getAttribute("mainNav");
			if (o != null) {
				MainNav mn = (MainNav) o;
				mn.setBooks(Boolean.valueOf(true));
				session.setAttribute("mainNav", mn);
			}

		}

		req.getRequestDispatcher("/WEB-INF/books/index.jsp").include(req, resp);
	}
}
