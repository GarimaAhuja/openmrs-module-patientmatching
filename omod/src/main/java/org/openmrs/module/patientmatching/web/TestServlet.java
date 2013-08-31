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

public class TestServlet extends HttpServlet {

	private Log log = LogFactory.getLog(getClass());
	HibernateFieldMetadataDAO hfm = new HibernateFieldMetadataDAO();	

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		//create fieldName to tablename+columname map
		HashMap<String, String> fieldNameToSchemaName = new HashMap<String, String>();
		fieldNameToSchemaName.put("(Attribute) Birthplace","person_attribute_type:Birthplace");
		fieldNameToSchemaName.put("(Attribute) Citizenship","person_attribute_type:Citizenship");

		//To get an entire column
		
		/*
		List fieldData = hfm.getField("person","person_id");
		for(int i=0;i<=fieldData.size();i++)
		{
			Object elem = result.get(i);
			out.println("<h1>" + elem + "</h1>");
		}*/
		
		//To get excluded properties
		AdministrationService adminService = Context.getAdministrationService();
		String excludedProperties = adminService.getGlobalProperty(MatchingConstants.CONFIG_EXCLUDE_PROPERTIES);
		List<String> listExcludedProperties = Arrays.asList(excludedProperties.split(",", -1));
		log.info("Excluded Properties: " + excludedProperties);

		//To get all fields
		PatientMatchingConfiguration pmc = new PatientMatchingConfiguration();
		pmc = MatchingConfigurationUtils.createPatientMatchingConfig(listExcludedProperties);
		SortedSet<ConfigurationEntry> ss = new TreeSet<ConfigurationEntry>();
		ss = pmc.getConfigurationEntries();
		int len = ss.size();
		String elem;
		for(int i=0;i<len;i++)
		{
			elem =  ss.first().getFieldName();
			if(fieldNameToSchemaName.containsKey(elem))
			{
				elem=fieldNameToSchemaName.get(elem);
			}
			out.println("<p>" + elem + "</p>");
			ss.remove(ss.first());
		}
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException
	{
		doGet(request, response);
	}

}
