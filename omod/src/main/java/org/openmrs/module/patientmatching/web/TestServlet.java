package org.openmrs.module.patientmatching.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.openmrs.module.patientmatching.db.hibernate.HibernateFieldMetadataDAO;

import java.util.List;

import org.openmrs.module.patientmatching.web.dwr.DWRStrategyUtilities;

import org.regenstrief.FieldMetrics.FieldMetricsCalculation.DataStructure.Field;

public class TestServlet extends HttpServlet {

	private Log log = LogFactory.getLog(getClass());
	HibernateFieldMetadataDAO hfm = new HibernateFieldMetadataDAO();	

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		DWRStrategyUtilities dsu = new DWRStrategyUtilities();
		
		List<String> fieldNames = dsu.getAllSuggestedFields();
		for(int j=0;j<fieldNames.size();j++)
		{
			out.println("<h4>"+ fieldNames.get(j) +"</h4>");
		}
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		doGet(request, response);
	}

}
