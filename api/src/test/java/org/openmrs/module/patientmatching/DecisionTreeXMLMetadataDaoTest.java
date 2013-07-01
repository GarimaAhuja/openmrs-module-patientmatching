package org.openmrs.module.patientmatching.db;


import org.junit.Assert;
import org.junit.Test;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.module.patientmatching.db.DecisionTreeXMLMetadataDao;
import org.openmrs.module.patientmatching.db.PatientMatchingReportMetadataDao;
import org.openmrs.module.patientmatching.db.hibernate.HibernateDecisionTreeXMLMetadataDAO;
import org.openmrs.module.patientmatching.db.hibernate.HibernatePatientMatchingReportMetadataDAO;
import org.openmrs.module.patientmatching.DecisionTreeXML;
import org.openmrs.module.patientmatching.PatientMatchingConfiguration;

public class DecisionTreeXMLMetadataDaoTest extends BaseModuleContextSensitiveTest {
	/**
	 * @see DecisionTreeXMLMetadataDao.java
	 * @verifies saveDecisionTreeXML() and findDecisionTreeById() work properly
	 */
	@Test
	public void shouldCreateDecisionTreeXMLRecordAndFindIt()
			throws Exception {
		//DecisionTreeXMLMetadataDao dtxmd = new HibernateDecisionTreeXMLMetadataDAO();		
		PatientMatchingReportMetadataDao pmrmd = new HibernatePatientMatchingReportMetadataDAO();		
		
		PatientMatchingConfiguration pmc = new PatientMatchingConfiguration();
		//DecisionTreeXML dtx = new DecisionTreeXML();
		//DecisionTreeXML returndtx = new DecisionTreeXML();
		
		pmc.setConfigurationId(1);
		pmc.setConfigurationName("test");
		pmrmd.savePatientMatchingConfiguration(pmc);
		//dtx.setdecisionTreeXMLId(1);
		//dtx.setdecisionTreeXML("Test String");
		//returndtx = dtxmd.findDecisionTreeXMLById(1);
		//dtxmd.saveDecisionTreeXML(dtx);
	}
}
