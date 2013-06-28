/**
 *
 */
package org.openmrs.module.patientmatching;

/**
 * A class to represent a xml file containing a xml encoded decision tree
 */

public class DecisionTreeXML implements Comparable<DecisionTreeXML>{

	// A unique Id to identify each decision tree xml
	private int decisionTreeXMLId;

	//to store the xml encoded tree
	private String decisionTreeXML;

	//constructor
	public DecisionTreeXML(){
		super();
	}

	/**
	 * Return the decisionTreeXMLId
	 * @return the decisionTreeXMLId
	 */
	public int getdecisionTreeXMLId() {
		return decisionTreeXMLId;
	}

	/**
	  * Set the decisionTreeXMLId
	  * @param the decisionTreeXMLId to set
	  */
	public void setdecisionTreeXMLId(int decisionTreeXMLId) {
		this.decisionTreeXMLId = decisionTreeXMLId;
	}

	/**
	 * Return the decisionTreeXML
	 * @return the decisionTreeXML
	 */
	public String getdecisionTreeXML() {
		return decisionTreeXML;
	}

	/**
	  * Set the decisionTreeXML
	  * @param the decisionTreeXML to set
	  */
	public void setdecisionTreeXML(String decisionTreeXMLId) {
		this.decisionTreeXML = decisionTreeXML;
	}

	public int compareTo(DecisionTreeXML o) {
		return 1;
	}

}
