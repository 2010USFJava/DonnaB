package com.revature.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MasterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException {
		req.getRequestDispatcher(RequestHelper.process(req)).forward(req,res);
	}
	
	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException {
		req.getRequestDispatcher(RequestHelper.process(req)).forward(req,res);
	}
	
}
