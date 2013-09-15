package org.openmrs.module.patientmatching.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Properties;

import org.regenstrief.FieldMetrics.tree.Node;
import org.regenstrief.FieldMetrics.predictAdvice;

public class TestServlet extends HttpServlet {

	private Log log = LogFactory.getLog(getClass());

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<h4>"+"Potato Naaa"+"</h4>");
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		doGet(request, response);
	}

}
