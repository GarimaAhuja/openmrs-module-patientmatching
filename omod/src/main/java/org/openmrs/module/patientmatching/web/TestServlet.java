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
import java.util.ArrayList;

import org.openmrs.module.patientmatching.MatchingConfigurationUtils;
import org.openmrs.module.patientmatching.PatientMatchingConfiguration;

public class TestServlet extends HttpServlet {

	private Log log = LogFactory.getLog(getClass());
	HibernateFieldMetadataDAO hfm = new HibernateFieldMetadataDAO();	

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		String message = "Potato naaa Banananan ";
		PrintWriter out = response.getWriter();
		out.println("<h1>" + message + "</h1>");

		List result = hfm.getField("person","person_id");
		for(int i=0;i<=10;i++)
		{
			Object elem = result.get(i);
			out.println("<h1>" + elem + "</h1>");
		}
		
		List<String> s1= new ArrayList<String>();
		//PatientMatchingConfiguration pmc = new PatientMatchingConfiguration();
		//pmc = MatchingConfigurationUtils.createPatientMatchingConfig(s1);

		
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		doGet(request, response);
	}

}
