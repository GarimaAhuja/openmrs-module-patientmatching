package org.openmrs.module.patientmatching.web.dwr;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.String;
import java.lang.Integer;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.proxy.dwr.Util;

import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.PersonService;
import org.openmrs.api.PatientService;
import org.openmrs.api.impl.PersonServiceImpl;
import org.openmrs.api.impl.PatientServiceImpl;
import org.openmrs.PersonAttributeType;
import org.openmrs.PatientIdentifierType;

import org.openmrs.module.patientmatching.MatchingConstants;
import org.openmrs.module.patientmatching.MatchingConfigurationUtils;
import org.openmrs.module.patientmatching.PatientMatchingConfiguration;
import org.openmrs.module.patientmatching.ConfigurationEntry;

import org.openmrs.module.patientmatching.db.hibernate.HibernateFieldMetadataDAO;

import org.regenstrief.FieldMetrics.predictAdvice;
import org.regenstrief.FieldMetrics.tree.Node;
import org.regenstrief.FieldMetrics.FieldMetricsCalculation.FieldMetricsImplementation;
import org.regenstrief.FieldMetrics.FieldMetricsCalculation.DataStructure.Field;

import java.lang.Runnable;
import java.lang.Thread;


/**
 * Utility class that will be available to the DWR javascript call from the
 * module web page. All methods in this class must be registered in module
 * config file to make it available as javascript call.
 */
public class DWRStrategyUtilities implements Runnable {

	protected final Log log = LogFactory.getLog(getClass());
	private List<String> suggestedFieldsInFormat = new ArrayList<String>();

	/**
	 * Constructor
	 */
	public DWRStrategyUtilities() {
	}
	
	
	public HashMap<String,String> getMap() {
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

		return fieldNameToSchemaName;
	}
	
	public HashMap<String,String> getInverseMap() {
		HashMap<String, String> schemaNameToFieldName = new HashMap<String, String>(); 
		schemaNameToFieldName.put("person_attribute_type:Birthplace","(Attribute) Birthplace");
		schemaNameToFieldName.put("person_attribute_type:Citizenship","(Attribute) Citizenship");
		schemaNameToFieldName.put("person_attribute_type:Civil Status","(Attribute) Civil Status");
		schemaNameToFieldName.put("person_attribute_type:Health Center","(Attribute) Health Center");
		schemaNameToFieldName.put("person_attribute_type:Health District","(Attribute) Health District");
		schemaNameToFieldName.put("person_attribute_type:Mother's Name","(Attribute) Mother's Name");
		schemaNameToFieldName.put("person_attribute_type:Race","(Attribute) Race");
		schemaNameToFieldName.put("patient_identifier_type:Old Identification Number","(Identifier) Old Identification Number");
		schemaNameToFieldName.put("patient_identifier_type:OpenMRS Identification Number","(Identifier) OpenMRS Identification Number");
		schemaNameToFieldName.put("person:birthdate","org.openmrs.Patient.birthdate");
		schemaNameToFieldName.put("person:birthdate_estimated","org.openmrs.Patient.birthdateEstimated");
		schemaNameToFieldName.put("person:dead","org.openmrs.Patient.dead");
		schemaNameToFieldName.put("person:death_date","org.openmrs.Patient.deathDate");
		schemaNameToFieldName.put("person:gender","org.openmrs.Patient.gender");
		schemaNameToFieldName.put("patient:patient_id","org.openmrs.Patient.patientId");
		schemaNameToFieldName.put("person:person_id","org.openmrs.Patient.personId");
		schemaNameToFieldName.put("person:uuid","org.openmrs.Patient.uuid");
		schemaNameToFieldName.put("person_address:address1","org.openmrs.PersonAddress.address1");
		schemaNameToFieldName.put("person_address:address2","org.openmrs.PersonAddress.address2");
		schemaNameToFieldName.put("person_address:address3","org.openmrs.PersonAddress.address3");
		schemaNameToFieldName.put("person_address:address4","org.openmrs.PersonAddress.address4");
		schemaNameToFieldName.put("person_address:address5","org.openmrs.PersonAddress.address5");
		schemaNameToFieldName.put("person_address:address6","org.openmrs.PersonAddress.address6");
		schemaNameToFieldName.put("person_address:city_village","org.openmrs.PersonAddress.cityVillage");
		schemaNameToFieldName.put("person_address:country","org.openmrs.PersonAddress.country");
		schemaNameToFieldName.put("person_address:county_district","org.openmrs.PersonAddress.countyDistrict");
		schemaNameToFieldName.put("person_address:end_date","org.openmrs.PersonAddress.endDate");
		schemaNameToFieldName.put("person_address:latitude","org.openmrs.PersonAddress.latitude");
		schemaNameToFieldName.put("person_address:longitude","org.openmrs.PersonAddress.longitude");
		schemaNameToFieldName.put("person_address:person_address_id","org.openmrs.PersonAddress.personAddressId");
		schemaNameToFieldName.put("person_address:postal_code","org.openmrs.PersonAddress.postalCode");
		schemaNameToFieldName.put("person_address:preferred","org.openmrs.PersonAddress.preferred");
		schemaNameToFieldName.put("person_address:start_date","org.openmrs.PersonAddress.startDate");
		schemaNameToFieldName.put("person_address:state_province","org.openmrs.PersonAddress.stateProvince");
		schemaNameToFieldName.put("person_address:uuid","org.openmrs.PersonAddress.uuid");
		schemaNameToFieldName.put("person_name:degree","org.openmrs.PersonName.degree");
		schemaNameToFieldName.put("person_name:family_name","org.openmrs.PersonName.familyName");
		schemaNameToFieldName.put("person_name:family_name2","org.openmrs.PersonName.familyName2");
		schemaNameToFieldName.put("person_name:family_name_prefix","org.openmrs.PersonName.familyNamePrefix");
		schemaNameToFieldName.put("person_name:family_name_suffix","org.openmrs.PersonName.familyNameSuffix");
		schemaNameToFieldName.put("person_name:given_name","org.openmrs.PersonName.givenName");
		schemaNameToFieldName.put("person_name:middle_name","org.openmrs.PersonName.middleName");
		schemaNameToFieldName.put("person_name:person_name_id","org.openmrs.PersonName.personNameId");
		schemaNameToFieldName.put("person_name:preferred","org.openmrs.PersonName.preferred");
	
		return schemaNameToFieldName;
	}

	/**
	  * get all possible fields for patient matching in the "tableName:columnName" format
	  */
	public List<String> getAllMatchingFields() {
		//create fieldName to tablename+columname map
		HashMap<String, String> fieldNameToSchemaName = new HashMap<String,String>(); 
		fieldNameToSchemaName = getMap();


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
		String tableName="";
		String columnName="";
		List<String> fieldData = new ArrayList<String>();
		
		StringTokenizer st = new StringTokenizer(fieldName,":");
		tableName = st.nextElement().toString();
		columnName = st.nextElement().toString();
		
		if(tableName.equals("person_attribute_type")||tableName.equals("patient_identifier_type"))
		{
			String whereColumn="";
			String whereValue="";
			if(tableName.equals("person_attribute_type"))
			{
				PersonService ps = Context.getPersonService();
				PersonAttributeType pat = new PersonAttributeType();
				pat = ps.getPersonAttributeTypeByName(columnName);
				int typeId = pat.getPersonAttributeTypeId();
				whereColumn = "person_attribute_type_id";
				whereValue = Integer.toString(typeId);
				tableName = "person_attribute";
				columnName = "value";
			}
			else
			{
				PatientService ps = Context.getPatientService();
				PatientIdentifierType pit = new PatientIdentifierType();
				pit = ps.getPatientIdentifierTypeByName(columnName);
				int typeId = pit.getPatientIdentifierTypeId();
				whereColumn = "identifier_type";
				whereValue = Integer.toString(typeId);
				tableName = "patient_identifier";
				columnName = "identifier";
			}
			List fieldDataAsObjects=hfm.getFieldWhere(tableName,columnName,whereColumn,whereValue);

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
	public List<Field> getDataFromDatabase()
	{
		int i;
		List<Field> fields =  new ArrayList<Field>();
		
		List<String> matchingFields = getAllMatchingFields();
		for(i=0;i<matchingFields.size();i++)
		{
			Field newField = new Field();
			newField.setFieldName(matchingFields.get(i));
			newField.fieldData = new ArrayList<String>();
			newField.fieldData = getDataForField(matchingFields.get(i));
			fields.add(newField);
		}
		return fields;
	}

	public List<String> getAllSuggestedFields() 
	{
		startThread();
		
		return suggestedFieldsInFormat;
	}

	public void run() 
	{
		List<String> suggestedFields = new ArrayList<String>();
		
		Node[] root = new Node[10];
		List<Field> fields;
		int i,j;
		int target;

		//getting DecisionTrees
		for(i=0;i<10;i++)
		{
			String pathToCurrentTree = getClass().getClassLoader().getResource("RandomForest/tree"+Integer.toString(i+1)+".xml").toString();
			root[i]=predictAdvice.getDecisionTree(pathToCurrentTree);
		}

		//getting field data from database
		fields=getDataFromDatabase();

		//calculating field metrics
		predictAdvice.calculateFieldMetrics(fields);
		
		//using field metrics and the decision tree to know whether a particular attribute is good for patient matching
		for(i=0;i<fields.size();i++)
		{
			//target >= 5 if field is good for matching
			target=0;
			for(j=0;j<10;j++)
			{
				target+=predictAdvice.decideTarget(root[j],fields.get(i));
			}
			if(target>=5)
			{
				fields.get(i).setTarget(1);
				suggestedFields.add(fields.get(i).getFieldName());
			}
			else
			{
				fields.get(i).setTarget(0);
			}
		}
		
		HashMap<String, String> schemaNameToFieldName = new HashMap<String,String>(); 
		schemaNameToFieldName = getInverseMap();
		
		
		for(i=0;i<suggestedFields.size();i++)
		{
			if(schemaNameToFieldName.containsKey(suggestedFields.get(i)))
			{
				suggestedFieldsInFormat.add(schemaNameToFieldName.get(suggestedFields.get(i)));
			}
		}
	}
	public void startThread()
	{
		Thread aThread = new Thread(this);
		aThread.run();
	}

}
