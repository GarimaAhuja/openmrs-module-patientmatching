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
import java.util.StringTokenizer;

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
import org.openmrs.Person;

import org.openmrs.module.patientmatching.MatchingConstants;
import org.openmrs.module.patientmatching.MatchingConfigurationUtils;
import org.openmrs.module.patientmatching.PatientMatchingConfiguration;
import org.openmrs.module.patientmatching.ConfigurationEntry;

import org.openmrs.module.patientmatching.db.hibernate.HibernateFieldMetadataDAO;

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
		fieldNameToSchemaName.put("(Attribute) Civil Status","person_attribute_type:Civil Status");
		fieldNameToSchemaName.put("(Attribute) Health Center","person_attribute_type:Health Center");
		fieldNameToSchemaName.put("(Attribute) Health District","person_attribute_type:Health District");
		fieldNameToSchemaName.put("(Attribute) Mother's Name","person_attribute_type:Mother's Name");
		fieldNameToSchemaName.put("(Attribute) Race","person_attribute_type:Race");
		fieldNameToSchemaName.put("(Identifier) Old Identification Number","patient_identifier_type:Old Identification Number");
		fieldNameToSchemaName.put("(Identifier) OpenMRS Identification Number","patient_identifier_type:OpenMRS Identification Number");
		fieldNameToSchemaName.put("org.openmrs.Patient.birthdate","person:birthdate");
		fieldNameToSchemaName.put("org.openmrs.Patient.birthdateEstimated","person:birthdate_estimated");
		fieldNameToSchemaName.put("org.openmrs.Patient.dead","person:dead");
		fieldNameToSchemaName.put("org.openmrs.Patient.deathDate","person:death_date");
		fieldNameToSchemaName.put("org.openmrs.Patient.gender","person:gender");
		fieldNameToSchemaName.put("org.openmrs.Patient.id","person:person_id");
		fieldNameToSchemaName.put("org.openmrs.Patient.patientId","patient:patient_id");
		fieldNameToSchemaName.put("org.openmrs.Patient.personId","person:person_id");
		fieldNameToSchemaName.put("org.openmrs.Patient.uuid","person:uuid");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.address1","person_address:address1");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.address2","person_address:address2");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.address3","person_address:address3");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.address4","person_address:address4");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.address5","person_address:address5");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.address6","person_address:address6");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.cityVillage","person_address:city_village");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.country","person_address:country");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.countyDistrict","person_address:county_district");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.endDate","person_address:end_date");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.id","person_address:person_address_id");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.latitude","person_address:latitude");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.longitude","person_address:longitude");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.personAddressId","person_address:person_address_id");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.postalCode","person_address:postal_code");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.preferred","person_address:preferred");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.startDate","person_address:start_date");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.stateProvince","person_address:state_province");
		fieldNameToSchemaName.put("org.openmrs.PersonAddress.uuid","person_address:uuid");
		fieldNameToSchemaName.put("org.openmrs.PersonName.degree","person_name:degree");
		fieldNameToSchemaName.put("org.openmrs.PersonName.familyName","person_name:family_name");
		fieldNameToSchemaName.put("org.openmrs.PersonName.familyName2","person_name:family_name2");
		fieldNameToSchemaName.put("org.openmrs.PersonName.familyNamePrefix","person_name:family_name_prefix");
		fieldNameToSchemaName.put("org.openmrs.PersonName.familyNameSuffix","person_name:family_name_suffix");
		fieldNameToSchemaName.put("org.openmrs.PersonName.givenName","person_name:given_name");
		fieldNameToSchemaName.put("org.openmrs.PersonName.id","person_name:person_name_id");
		fieldNameToSchemaName.put("org.openmrs.PersonName.middleName","person_name:middle_name");
		fieldNameToSchemaName.put("org.openmrs.PersonName.personNameId","person_name:person_name_id");
		fieldNameToSchemaName.put("org.openmrs.PersonName.preferred","person_name:preferred");


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
	}
	//returns column data give fieldName in the format returned by getAllMatchingFields() function
	public List<String> getDataForField(String fieldName)
	{
		HibernateFieldMetadataDAO hfm = new HibernateFieldMetadataDAO();
		String tableName,columnName;
		List<String> fieldData = new ArrayList<String>();
		
		StringTokenizer st = new StringTokenizer(fieldName,":");
		tableName = st.nextElement().toString();
		columnName = st.nextElement().toString();
		
		if(tableName.equals("person_attribute_type")||tableName.equals("patient_identifier_type"))
		{

			/*
			for(int i=0;i<fieldDataAsObjects.size();i++)
			{
				String elem = new String();
				if(fieldDataAsObjects.get(i)!=null)
				{
					elem = fieldDataAsObjects.get(i).toString();
				}
				else
				{
					elem = "";
				}
				fieldData.add(elem);
			}*/
		}
		else
		{
			List fieldDataAsObjects = hfm.getField(tableName,columnName);
			for(int i=0;i<fieldDataAsObjects.size();i++)
			{
				String elem = new String();
				if(fieldDataAsObjects.get(i)!=null)
				{
					elem = fieldDataAsObjects.get(i).toString();
				}
				else
				{
					elem = "";
				}
				fieldData.add(elem);
			}
		}

		return fieldData;
	}
}
