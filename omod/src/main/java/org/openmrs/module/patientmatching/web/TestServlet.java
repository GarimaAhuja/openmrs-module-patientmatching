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

import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;

import java.lang.String;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.HashMap;

import org.openmrs.module.patientmatching.MatchingConstants;
import org.openmrs.module.patientmatching.MatchingConfigurationUtils;
import org.openmrs.module.patientmatching.PatientMatchingConfiguration;
import org.openmrs.module.patientmatching.ConfigurationEntry;
import org.openmrs.module.patientmatching.web.dwr.DWRStrategyUtilities;

public class TestServlet extends HttpServlet {

	private Log log = LogFactory.getLog(getClass());
	HibernateFieldMetadataDAO hfm = new HibernateFieldMetadataDAO();	

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		//To get an entire column
		DWRStrategyUtilities dsu = new DWRStrategyUtilities();
		List<String> fieldNames = dsu.getAllMatchingFields();
		for(int j=0;j<fieldNames.size();j++)
		{
			//out.println("<h4>"+ fieldNames.get(j) +"<h4>");
			List fieldData = dsu.getDataForField(fieldNames.get(j));
			//for(int i=0;i<fieldData.size();i++)
				//out.println("<p>"+fieldData.get(i)+"</p>");
		}
		List fieldData = dsu.getDataForField("person_attribute:value");
			for(int i=0;i<fieldData.size();i++)
				out.println("<p>"+fieldData.get(i)+"</p>");
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		doGet(request, response);
	}

}
