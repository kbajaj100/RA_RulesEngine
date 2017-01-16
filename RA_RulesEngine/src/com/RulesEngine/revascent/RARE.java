package com.RulesEngine.revascent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.sun.rowset.CachedRowSetImpl;

public class RARE {

	private static String dbUrl ="";
	
	static DBConn myconn = new DBConn();
	int numrules;
	int RUN_ID = 1;
	static DBIndex myDBindex = new DBIndex();
	
	String SQL;
	
	static ArrayList<Integer> RuleList = new ArrayList<Integer>();
	static int Run_ID = 0;
	static LocalDateTime Datetime;
	LeftRule[] myLR = new LeftRule[1]; // creates an array of the LR object which has all the logic for the Left Rules
	RightRule[] myRight = new RightRule[1];
	
	public void execRules() throws FileNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub

		initiateDBConn();
		String Claims;
		
		numrules = getnumrules();
		myLR = new LeftRule[numrules];
		myRight = new RightRule[numrules];
		
		System.out.println("Number of Active rules is: " + numrules);

		createRuleList();
		
		getRunID();
		
				
		for (int i = 0; i< numrules; ++i){
			myLR[i] = new LeftRule();
			Claims = getLeftClaimList(i, myLR);
			
			myRight[i] = new RightRule();
			Claims = getRightClaimList(i, Claims);
		}
		
	}

	private void getRunID() {
		// TODO Auto-generated method stub
		DBRun myrun = new DBRun();
		
		SQL = myrun.getRunIDSQL();
		
		RUN_ID = (myconn.execSQL_returnint(SQL));
	}

	private String getRightClaimList(int i, String claims) {
		// TODO Auto-generated method stub
		
		return claims;
	}

	private String getLeftClaimList(int k, LeftRule[] myLR) {
		// k is counter in the LeftRule object array
		
		int left_sub_count;
		
		String SQL_out = "select a1.CLM_ID from ("; 
		
		myLR[k].setRuleID(RuleList.get(k));
		SQL = myLR[k].getSQL_subrulecount();
		left_sub_count = myconn.execSQL_returnint(SQL);
		myLR[k].setLeft_sub_count(left_sub_count);
			
		// This for loop is for each sub rule within the rule
		// End goal is to get the Left SQL for that rule
		for (int j = 1; j <= left_sub_count; ++j){ 

			if ((j == 1))// && (left_sub_count == 1))
			{
				SQL = myLR[k].getLeftRuleTypeID(j);
				myLR[k].setLeftRuleTypeID(myconn.execSQL_returnint(SQL));
				SQL_out = SQL_out + myLR[k].getRuleSQL(j) + ") a" + j;
			}
			else if ((j>1) && (left_sub_count > 1))
			{
				SQL = myLR[k].getLeftRuleTypeID(j);
				myLR[k].setLeftRuleTypeID(myconn.execSQL_returnint(SQL));
				SQL_out = SQL_out + " join (" + myLR[k].getRuleSQL(j) + ") a" + j + " " + 
						  "on (a" + j + ".CLM_ID = a" + (j-1) + ".CLM_ID)";
			}
			
			System.out.println("SQL_out for Rule: " + RuleList.get(k) + " is: " + SQL_out);
		}

		return(getLeftSQL_out_result(SQL_out, k));
		
	}
	
	private String getLeftSQL_out_result(String SQL_out, int k){
		
		String Claim_list = "";
		if(!myconn.execSQL_crs(SQL_out))
			System.exit(1);
		
	    try {

	    	CachedRowSetImpl crs = new CachedRowSetImpl();
	    	crs = myconn.getRowSet();

	    	while (crs.next()) {
	    			    		
	    		if (Claim_list.equals(""))
	    			Claim_list =  Integer.toString(crs.getInt(1));
	    		else Claim_list =  Claim_list + "," + Integer.toString(crs.getInt(1));

	    		SQL = myLR[k].getSQL(crs.getInt(1), RUN_ID);
	    		myconn.execSQL(SQL);
	    	}

	    } catch (SQLException se){
	    	se.printStackTrace();
	    }		
	    
	    
	    System.out.println("");
	    System.out.println("Claims List for Rule ID: " + RuleList.get(k) + " is " + Claim_list);
	    
		return Claim_list;
		
	}

	private void initiateDBConn() throws FileNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		
		dbUrl = getdbUrl();
		
	}

	public static String getdbUrl() throws FileNotFoundException, IOException, SQLException{
		//Returns the number of rule IDs in the database
		
		myconn.setDBConn("C:/Props/RulesEngine/DBprops.properties");
		
		dbUrl = myconn.getdbUrl();
		
		//System.out.println(dbUrl);
		return dbUrl;
	}
	
	private int getnumrules() {
		// TODO Auto-generated method stub
		
		SQL = "select count(Rule_ID) count " +
			  "from " + myDBindex.RS_Index + " " + 
			  "where Status = 'A'";
		 
		return myconn.execSQL_returnint(SQL);
	}
	
	private void createRuleList() {
		// TODO Auto-generated method stub
		
		SQL = "select cast(Rule_ID as int)" + 
			  "from " + myDBindex.getRS_Index() + " " + 
			  "where Status = 'A'";
		
		System.out.println(SQL);
		
		int arrcounter = 0;
		
		if(!myconn.execSQL_crs(SQL))
			System.exit(1);
		
	    try {

	    	CachedRowSetImpl crs = new CachedRowSetImpl();
	    	crs = myconn.getRowSet();

	    	while (crs.next()) {
	    		
	    		System.out.println("RuleID is: " + crs.getInt(1));	
	    		RuleList.add(arrcounter, crs.getInt(1));
	    		++arrcounter;
	    	}

	    } catch (SQLException se){
	    	se.printStackTrace();
	    }
		
	    displayRuleList(arrcounter);
	}

	private void displayRuleList(int arc) {
		// TODO Auto-generated method stub
		
		for(int i = 0; i < arc; ++i){
			System.out.println("Rule ID in Rule List is: " + RuleList.get(i));
		}
	}
	
}
