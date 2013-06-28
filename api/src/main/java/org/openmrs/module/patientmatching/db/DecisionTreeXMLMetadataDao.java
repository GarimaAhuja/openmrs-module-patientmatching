package org.openmrs.module.patientmatching.db;
import java.util.List;
import org.openmrs.module.patientmatching.DecisionTreeXML;

/**
 * The DAO class to access database objects
 */
public interface DecisionTreeXMLMetadataDao {

	/**
	 * Persists the given DecisionTreeXML to the database
	 * @param DecisionTreeXML the xml encoded tree to be persisted
	 */
	public void saveDecisionTreeXML(DecisionTreeXML decisionTreeXML);

	/**
	 * Find and return the DecisionTreeXML with the given id if exists in the database
	 * @param decisionTreeXMLId The id of the DecisionTreeXML to be loaded
	 * @return DecisionTreeXML with the given id
	 */
	public DecisionTreeXML findDecisionTreeXMLById(int decisionTreeXMLId);

	/**
	 * Get the list of all the DecisionTreeXML in the database
	 * @return the list of all the DecisionTreeXML in the database
	 */
	public List<DecisionTreeXML> getAllDecisionTreeXML();

	/**
	 * Delete the DecisonTreeXML which has the given Id
	 * @param the decisionTreeXMLId of DecisionTREEXML to be deleted
	 */
	public void deleteDecisionTreeXMLById(int decisionTreeXMLId);

}

