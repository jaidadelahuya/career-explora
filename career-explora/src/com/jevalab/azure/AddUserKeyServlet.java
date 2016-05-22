package com.jevalab.azure;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jevalab.exceptions.PreexistingEntityException;
import com.jevalab.exceptions.RollbackFailureException;

public class AddUserKeyServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			KeyGenerator.persistKeys(KeyGenerator.generateKeys());
		} catch (PreexistingEntityException e) {
			
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, "Duplicate Entry");
		} catch (RollbackFailureException e) {
			
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, "Rollback failed");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, "Could not complete save operation");
		}
		
		resp.getWriter().write("save complete");
	}
}
