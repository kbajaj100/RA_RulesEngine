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
		
		System.out.println("Run ID is: " + RUN_ID);
		for (int i = 0; i< numrules; ++i){
			myLR[i] = new LeftRule();
			myLR[i].setRUN_ID(RUN_ID);
			Claims = getLeftClaimList(i);
			
			myRight[i] = new RightRule();
			myRight[i].setRUN_ID(RUN_ID);
			Claims = getRightClaimList(i, Claims);
		}		
	}

	private void getRunID() {
		// TODO Auto-generated method stub
		DBRun myrun = new DBRun();		
		SQL = myrun.getRunIDSQL();		
		RUN_ID = (myconn.execSQL_returnint(SQL));
	}

	private String getRightClaimList(int i, String claims) throws FileNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		
		int right_rule_type = 0;
		int line_number = 0;
		int line_count = 0;
		
		System.out.println();
		
		
		myRight[i].setRuleID(RuleList.get(i));	
		//For each rule, get the # of sub rules
		myRight[i].setRight_sub_count(myconn.execSQL_returnint(myRight[i].getSQL_subrulecount()));
		
		System.out.println("Number of sub rules is: " + myRight[i].getRight_sub_count());
		
		//for loop, get the rule type for each sub rule
		//get the SQL for the sub rule
		//run the SQL and insert the claims into the table
		//loop
		
		//j is the counter for the sub rules
		for(int j = 1; j <= myRight[i].getRight_sub_count(); ++j)
		{
			//Set the Sub Rule ID
			myRight[i].setRight_sub(j);
			System.out.println("Sub RUle ID is: " + j);
			
			//Set the Right Rule Type ID
			myRight[i].setRightRuleTypeID(myconn.execSQL_returnint(myRight[i].getSQL_RightRuleTypeID(j)));
			
			//Set the number of lines in the Sub Rule
			line_count = myconn.execSQL_returnint(myRight[i].getSQL_Rule_Line_Count(j));
			System.out.println("line_Count is : " + line_count);
			myRight[i].setSub_Line_Count(line_count);
			
			//Set the starting line number for the sub rule
			line_number = myconn.execSQL_returnint(myRight[i].getSQL_Rule_Line_ID(j));
			myRight[i].setRule_Line_ID(line_number);
			
			//Set the search type
			myRight[i].setSearch_Type(myconn.execSQL_returnString(myRight[i].getSQL_searchtype(j)));
			
			//Set the search type count
			myRight[i].setSearch_type_count(myconn.execSQL_returnint(myRight[i].getSQL_searchtypecount(j)));
			
			//Set the value of the search code in that cell of the array for that Rule, Sub Rule, Line
			myRight[i].setCode(myconn.execSQL_returnString(myRight[i].getSQL_code(j, line_number)));
			
			//Set the value of the base rule
			//myRight[i].setBase_Rule_Type_ID(myconn.execSQL_returnint(myRight[i].getSQL_Base_Rule(j)));
			
			SQL = "insert into " + myDBindex.Flagged_Table + 
						  "(CLM_ID, CPT_CODE, Rule_ID, Sub_Rule_ID, RUN_ID)" + myRight[i].getSQL_Rule(j, claims);
					
			System.out.println("insert SQL is: " + SQL);
			myconn.execSQL(SQL);
				
		}
		
		myconn.execSQL("drop table "  + myDBindex.getRight_3());
		return claims;
	}

	private String getLeftClaimList(int k) throws FileNotFoundException, IOException, SQLException {
		// k is counter in the LeftRule object array
		
		int left_sub_count, left_rule_type = 0;
		
		String SQL_out = "select a1.CLM_ID from ("; 
		
		myLR[k].setRuleID(RuleList.get(k));
		SQL = myLR[k].getSQL_subrulecount();
		left_sub_count = myconn.execSQL_returnint(SQL);
		myLR[k].setLeft_sub_count(left_sub_count);
		
			
		// This for loop is for each sub rule within the rule
		// End goal is to get the Left SQL for that rule
		// j is the counter for the sub rule
		for (int j = 1; j <= left_sub_count; ++j){ 

			SQL = myLR[k].getSQL_LeftRuleTypeID(j);
			left_rule_type = myconn.execSQL_returnint(SQL);
			myLR[k].setLeftRuleTypeID(left_rule_type);
			
			//Get the SQL for the search type and, get the search type and then set it
			myLR[k].setSearch_Type(myconn.execSQL_returnString(myLR[k].getSQL_searchtype(j)));

			if ((j == 1))// && (left_sub_count == 1))
			{
				SQL_out = SQL_out + myLR[k].getSQL_Rule(j) + ") a" + j;
			}
			else if ((j>1) && (left_sub_count > 1))
			{
				SQL_out = SQL_out + " join (" + myLR[k].getSQL_Rule(j) + ") a" + j + " " + 
						  "on (a" + j + ".CLM_ID = a" + (j-1) + ".CLM_ID)";
			}
			
			System.out.println("SQL_out for Rule: " + RuleList.get(k) + " is: " + SQL_out);
		}

		return(getLeftSQL_out_result(SQL_out, k, left_rule_type));
		
	}

	private String getLeftSQL_out_result(String SQL_out, int k, int left_rule_type){
		
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
	    		
	    		SQL = myLR[k].getSQL_insert(crs.getInt(1), RUN_ID);
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
		
		SQL = "select cast(Rule_ID as int) " + 
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
