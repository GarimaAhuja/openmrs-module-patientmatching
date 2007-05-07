package org.regenstrief.linkage.util;
/*
 * Code refactored in February 2007.  The MatchingConfig boject
 * was disentangled from the GUI and other classes added to hold
 * the 
 */

import java.util.*;
import java.text.*;

public class MatchingConfig {
	
	// options for the algorithms
	public static final String[] ALGORITHMS = {"Exact Match", "JWC", "LCS", "LEV"};
	public static final int EXACT_MATCH = 0;
	public static final int JWC = 1;
	public static final int LCS = 2;
	public static final int LEV = 3;
	
	// datatype constants
	public static final int NUMERIC_TYPE = 0;
	public static final int STRING_TYPE = 1;
	
	private NumberFormat double_format;
	private static final int DOUBLE_SIG_FIGS = 5;
	
	final static double META_ONE = 0.99999;
	final static double META_ZERO = 0.00001;
	
	private List<MatchingConfigRow> row_options;
	
	private String name;
	private boolean estimate;
	
	public MatchingConfig(String name, String[] rn){
		row_options = new ArrayList<MatchingConfigRow>();
		for(int i = 0; i < rn.length; i++){
			MatchingConfigRow mcr = new MatchingConfigRow(rn[i]);
			row_options.add(mcr);
		}
		this.name = name;
		estimate = false;
		double_format = NumberFormat.getInstance();
		double_format.setMaximumFractionDigits(DOUBLE_SIG_FIGS);
	}
	
	public MatchingConfig(String name, MatchingConfigRow[] mcrs){
		row_options = new ArrayList<MatchingConfigRow>();
		this.name = name;
		estimate = false;
		for(int i = 0; i < mcrs.length; i++){
			row_options.add(mcrs[i]);
		}
		double_format = NumberFormat.getInstance();
		double_format.setMaximumFractionDigits(DOUBLE_SIG_FIGS);
	}
	
	public List<MatchingConfigRow> getMatchingConfigRows(){
		return row_options;
	}
	
	public void setEstimate(boolean b){
		estimate = b;
	}
	
	public boolean isEstimated(){
		return estimate;
	}
	
	public String[] getRowNames(){
		String[] ret = new String[row_options.size()];
		for(int i = 0; i < row_options.size(); i++){
			ret[i] = row_options.get(i).getName();
		}
		return ret;
	}
	
	public String getRowName(int index){
		return row_options.get(index).getName();
	}
	
	/*
	 * Method created for FormPairs and when comparing string values to determine
	 * whether null values should be compared as empty strings or thrown away as
	 * not enough information.  The default is true, to ignore lines containing null values
	 */
	public boolean ignoreNullValues(int index){
		return true;
	}
	
	public int getAlgorithm(int row_index){
		return row_options.get(row_index).getAlgorithm();
	}
	
	public void setAlgorithm(int row_index, int algorithm){
		row_options.get(row_index).setAlgorithm(algorithm);
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String str){
		name = str;
	}
	
	public double getAgreementValue(int row_index){
		return row_options.get(row_index).getAgreement();
	}
	
	public void setAgreementValue(int row_index, double value){
		row_options.get(row_index).setAgreement(value);
	}
	
	public double getNonAgreementValue(int row_index){
		return row_options.get(row_index).getNonAgreement();
	}
	
	public void setNonAgreementValue(int row_index, double value){
		row_options.get(row_index).setNonAgreement(value);
	}
	
	public int getRowIndexforName(String name){
		// returns the row that has name as the name
		for(int i = 0; i < row_options.size(); i++){
			if(row_options.get(i).getName().equals(name)){
				// found the index
				return i;
			}
		}
		
		// it was not found, return -1 to signify error
		return -1;
	}
	
	public List<MatchingConfigRow> getIncludedColumns(){
		ArrayList<MatchingConfigRow> ret = new ArrayList<MatchingConfigRow>();
		Iterator<MatchingConfigRow> it = row_options.iterator();
		while(it.hasNext()){
			MatchingConfigRow mcr = it.next();
			if(mcr.isIncluded()){
				ret.add(mcr);
			}
		}
		
		return ret;
	}
	
	/*
	 * Returns the indexes of the columns included in the matching run
	 * 
	 * Method returns an array of the row names instead of the order
	 * within the row_options list, since the order of the MatchingConfigRow
	 * objects within the list is independent of the column indexes in the
	 * data file
	 */
	public String[] getIncludedColumnsNames(){
		int include_count = 0;
		Iterator<MatchingConfigRow> it = row_options.iterator();
		while(it.hasNext()){
			MatchingConfigRow mcr = it.next();
			if(mcr.isIncluded()){
				include_count++;
			}
		}
		if(include_count == 0){
			return null;
		}
		String[] ret = new String[include_count];
		int ret_index = 0;
		for(int i = 0; i < row_options.size(); i++){
			MatchingConfigRow mcr = row_options.get(i);
			if(mcr.isIncluded()){
				ret[ret_index] = mcr.getName();
				ret_index++;
			}
		}
		return ret;
	}
	
	public String[] getBlockingColumns(){
		int blocking_count = 0;
		Iterator<MatchingConfigRow> it = row_options.iterator();
		while(it.hasNext()){
			MatchingConfigRow mcr = it.next();
			if(mcr.getBlockOrder() != MatchingConfigRow.DEFAULT_BLOCK_ORDER){
				blocking_count++;
			}
		}
		if(blocking_count == 0){
			return null;
		}
		String[] ret = new String[blocking_count];
		for(int i = 0; i < row_options.size(); i++){
			MatchingConfigRow mcr = row_options.get(i);
			int order = mcr.getBlockOrder();
			if(order != MatchingConfigRow.DEFAULT_BLOCK_ORDER){
				ret[order - 1] = mcr.getName();
			}
		}
		return ret;
	}
	
	public String toString(){
		String ret = new String();
		Iterator<MatchingConfigRow> it = getMatchingConfigRows().iterator();
		while(it.hasNext()){
			MatchingConfigRow mcr = it.next();
			ret += mcr + "\n";
		}
		return ret;
	}
}