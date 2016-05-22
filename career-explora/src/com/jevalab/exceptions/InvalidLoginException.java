package com.jevalab.exceptions;

import javax.servlet.ServletException;



public class InvalidLoginException extends ServletException {

	public InvalidLoginException() {
		super("Invalid username/password");
	}
}
