package org.openmrs.module.patientmatching.web.dwr;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.String;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.proxy.dwr.Util;

import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;

import org.openmrs.module.patientmatching.MatchingConstants;
import org.openmrs.module.patientmatching.MatchingConfigurationUtils;
import org.openmrs.module.patientmatching.PatientMatchingConfiguration;
import org.openmrs.module.patientmatching.ConfigurationEntry;

/**
 * Utility class that will be available to the DWR javascript call from the
 * module web page. All methods in this class must be registered in module
 * config file to make it available as javascript call.
 */
public class DWRStrategyUtilities {

	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * Constructor
	 */
	public DWRStrategyUtilities() {
	}

	/**
	  * get all possible fields for patient matching in the "tableName:columnName" format
	  */
	public List<String> getAllMatchingFields() {
		//create fieldName to tablename+columname map
		HashMap<String, String> fieldNameToSchemaName = new HashMap<String, String>();
		fieldNameToSchemaName.put("(Attribute) Birthplace","person_attribute_type:Birthplace");
		fieldNameToSchemaName.put("(Attribute) Citizenship","person_attribute_type:Citizenship");


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
		String currentConfigEntry;
		List<String> matchingFields = new ArrayList<String>();
		for(int i=0;i<len;i++)
		{
			currentConfigEntry =  ss.first().getFieldName();
			if(fieldNameToSchemaName.containsKey(currentConfigEntry))
			{
				matchingFields.add(fieldNameToSchemaName.get(currentConfigEntry));
			}
			ss.remove(ss.first());
		}
		return matchingFields;
		//List<String> test = new ArrayList<String>();
		//test.add("Potato naaa");
		//return test;
	}
}
