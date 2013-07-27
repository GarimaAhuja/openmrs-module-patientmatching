package org.openmrs.module.patientmatching.web;

import java.io.IOException;

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

	private Log log = LogFactory.getLog(CRUDServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			DecisionTreeXMLMetadataDao dtxmd = new HibernateDecisionTreeXMLMetadataDAO();		

			DecisionTreeXML dtx = new DecisionTreeXML();
			DecisionTreeXML returndtx = new DecisionTreeXML();

			dtx.setdecisionTreeXMLId(1);
			dtx.setdecisionTreeXML("Test String");
			dtxmd.saveDecisionTreeXML(dtx);
			//returndtx = dtxmd.findDecisionTreeXMLById(1);
		}
		catch (Exception e) {
			log.error(e);
		}
	}
}
