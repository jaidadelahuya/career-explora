package com.jevalab.azure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jevalab.azure.persistence.EnglishCategory;
import com.jevalab.azure.persistence.EnglishCategoryJpaController;
import com.jevalab.exceptions.PreexistingEntityException;
import com.jevalab.exceptions.RollbackFailureException;

public class AddEnglishCategoryServlet extends HttpServlet {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String catName = req.getParameter("name");
		String instruction = req.getParameter("instruction");
		
		EnglishCategory ec = new EnglishCategory(catName, instruction);
		EnglishCategoryJpaController c1 = new EnglishCategoryJpaController();
		try {
			c1.create(ec);
		} catch (PreexistingEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
