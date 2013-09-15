package org.regenstrief.FieldMetrics;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import org.regenstrief.FieldMetrics.tree.Node;
import org.regenstrief.FieldMetrics.FieldMetricsCalculation.FieldMetricsImplementation;
import org.regenstrief.FieldMetrics.FieldMetricsCalculation.DataStructure.Field;

import java.lang.Integer;

public class predictAdvice 
{	
	public static Node getDecisionTree(String xmlFile) 
	{
		//parsing xmlFile to retrieve the xml encoded decision tree
		
		/*
		   The while loop updates the XPATH to get the nodes at level 0 then level 1 and so on .... 
		   it breaks out of the while loop when there are no nodes found at some level.
		   */

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setNamespaceAware(true);
		DocumentBuilder builder;
		String XPATH="";
		String elementAttribute="";
		String elementValue="";
		int level = 0;
		Node root = new Node();

		while(true)
		{
			XPATH+="/node";
			try {
				builder = docFactory.newDocumentBuilder();
				Document doc = builder.parse(xmlFile);
				XPathExpression expr = XPathFactory.newInstance().newXPath().compile(XPATH);
				Object hits = expr.evaluate(doc, XPathConstants.NODESET ) ;
				if ( hits instanceof NodeList ) {
					NodeList list = (NodeList) hits ;
					
					if(list.getLength()==0)
						break;
					
					//getting all the nodes at level (level-1), they would be parent nodes for the current level
					
					//please note that the node with attribute "Target" won't be a parent node for any node. 
					//Because when we reach the "Target" node, we already have a class, hence decision tree won't branch any further.
					//Thus this approach would work with unbalanced (height balance) trees as well.

					//The list will act as a queue. We start with root and at the end we have all the elements of the desired level
					
					List<Node> currLevelNodes = new ArrayList<Node>();
					if(level!=0)
					{
						currLevelNodes.add(root);
						while(currLevelNodes.get(0).getLevel()!=(level-1))
						{
							if(!currLevelNodes.get(0).leftChild.getAttribute().equals("Target"))
							{
								currLevelNodes.add(currLevelNodes.get(0).leftChild);
							}
							if(!currLevelNodes.get(0).rightChild.getAttribute().equals("Target"))
							{
								currLevelNodes.add(currLevelNodes.get(0).rightChild);
							}
							currLevelNodes.remove(0);
						}
					}


					for (int i = 0; i < list.getLength(); i++ ) {
						Element element = (Element) list.item(i);
						elementAttribute = element.getAttribute("attribute");
						elementValue = element.getAttribute("value");

						Node newNode = new Node();
						newNode.setValue(Float.parseFloat(elementValue));
						newNode.setAttribute(elementAttribute);
						newNode.setLevel(level);

						//inserting current level nodes in the tree
						if(level==0)
							root = newNode;
						else
						{
							if(i%2==0)
								currLevelNodes.get(i/2).leftChild=newNode;
							else
								currLevelNodes.get(i/2).rightChild=newNode;
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			level++;
		}
		return root;
	}
	public static void calculateFieldMetrics(List<Field> fields)
	{
		int i;

		//calculating field metrics
		for(i=0;i<fields.size();i++)
		{
			//getting Shannon Entropy
			fields.get(i).setH(FieldMetricsImplementation.calculateShannonEntropy(fields.get(i).fieldData));

			//getting number of unique values
			fields.get(i).setUqVal(FieldMetricsImplementation.calculateUniqueValues(fields.get(i).fieldData));

			//getting number of nulls
			fields.get(i).setN(FieldMetricsImplementation.calculateNumberOfNulls(fields.get(i).fieldData));

			//getting average frequency
			fields.get(i).setFavg(FieldMetricsImplementation.calculateAverageFrequency(fields.get(i).fieldData.size(),fields.get(i).getUqVal()));

			//getting HMax
			fields.get(i).setHMax(FieldMetricsImplementation.calculateHMax(fields.get(i).fieldData.size(),fields.get(i).getUqVal(),fields.get(i).getFavg()));

			//getting UVal
			fields.get(i).setUVal(FieldMetricsImplementation.calculateUVal(fields.get(i).fieldData,fields.get(i).fieldData.size()));

			//getting pairs
			fields.get(i).setPairs(FieldMetricsImplementation.calculatePairs(fields.get(i).fieldData));

			//getting log(pairs)
			fields.get(i).setLogPairs(FieldMetricsImplementation.calculateLogPairs(fields.get(i).getPairs()));	

			//getting HMaxPercent
			fields.get(i).setHMaxPercent(FieldMetricsImplementation.calculateHMaxPercent(fields.get(i).getH(),fields.get(i).getHMax()));	

			//getting NPercent			
			fields.get(i).setNPercent(FieldMetricsImplementation.calculateNPercent(fields.get(i).getN(),fields.get(i).fieldData.size()));	
		}
	}
	public static int decideTarget(Node root,Field field)
	{
		int ret = 0;
		if(root.getAttribute().equals("Target"))
		{
			//rounding off
			if(root.getValue()>0.5)
				ret = 1;
			else
				ret = 0;
		}
		else
		{
			if(root.getAttribute().equals("H"))
			{
				if(field.getH()<root.getValue())
				{
					ret = decideTarget(root.leftChild,field);
				}
				else
				{
					ret = decideTarget(root.rightChild,field);
				}
			}
			else if(root.getAttribute().equals("Hmax"))
			{
				if(field.getHMax()<root.getValue())
				{
					ret = decideTarget(root.leftChild,field);
				}
				else
				{
					ret = decideTarget(root.rightChild,field);
				}
			}
			else if(root.getAttribute().equals("Hmax%"))
			{
				if(field.getHMaxPercent()<root.getValue())
				{
					ret = decideTarget(root.leftChild,field);
				}
				else
				{
					ret = decideTarget(root.rightChild,field);
				}
			}
			else if(root.getAttribute().equals("UqVal"))
			{
				if(field.getUqVal()<root.getValue())
				{
					ret = decideTarget(root.leftChild,field);
				}
				else
				{
					ret = decideTarget(root.rightChild,field);
				}
			}
			else if(root.getAttribute().equals("Favg"))
			{
				if(field.getFavg()<root.getValue())
				{
					ret = decideTarget(root.leftChild,field);
				}
				else
				{
					ret = decideTarget(root.rightChild,field);
				}
			}
			else if(root.getAttribute().equals("N"))
			{
				if(field.getN()<root.getValue())
				{
					ret = decideTarget(root.leftChild,field);
				}
				else
				{
					ret = decideTarget(root.rightChild,field);
				}
			}
			else if(root.getAttribute().equals("N%"))
			{
				if(field.getNPercent()<root.getValue())
				{
					ret = decideTarget(root.leftChild,field);
				}
				else
				{
					ret = decideTarget(root.rightChild,field);
				}
			}
			else if(root.getAttribute().equals("Uval"))
			{
				if(field.getUVal()<root.getValue())
				{
					ret = decideTarget(root.leftChild,field);
				}
				else
				{
					ret = decideTarget(root.rightChild,field);
				}
			}
			else if(root.getAttribute().equals("pairs"))
			{
				if(field.getPairs()<root.getValue())
				{
					ret = decideTarget(root.leftChild,field);
				}
				else
				{
					ret = decideTarget(root.rightChild,field);
				}
			}
			else if(root.getAttribute().equals("log_pairs"))
			{
				if(field.getLogPairs()<root.getValue())
				{
					ret = decideTarget(root.leftChild,field);
				}
				else
				{
					ret = decideTarget(root.rightChild,field);
				}
			}
		}
		return ret;
	}
}
