package org.openmrs.module.patientmatching.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.openmrs.module.patientmatching.db.DecisionTreeXMLMetadataDao;
import org.openmrs.module.patientmatching.db.hibernate.HibernateDecisionTreeXMLMetadataDAO;
import org.openmrs.module.patientmatching.DecisionTreeXML;

public class CRUDServlet extends HttpServlet {

	private Log log = LogFactory.getLog(getClass());

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		DecisionTreeXMLMetadataDao dtxmd = new HibernateDecisionTreeXMLMetadataDAO();		

		DecisionTreeXML dtx = new DecisionTreeXML();
		DecisionTreeXML returndtx = new DecisionTreeXML();

		dtx.setdecisionTreeXMLId(1);
		dtx.setdecisionTreeXML("Test String");
		//dtxmd.saveDecisionTreeXML(dtx);
		returndtx = dtxmd.findDecisionTreeXMLById(1);
		
		String message = "Potato naaa Banananan ";
		PrintWriter out = response.getWriter();
		out.println("<h1>" + message + "</h1>");
	}


	@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
		{
			doGet(request, response);
		}

}
