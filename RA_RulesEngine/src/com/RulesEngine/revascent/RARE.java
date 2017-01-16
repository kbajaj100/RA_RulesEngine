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
	static DBIndex myDBindex = new DBIndex();
	
	String SQL;
	
	static ArrayList<Integer> RuleList = new ArrayList<Integer>();
	static int Run_ID = 0;
	static LocalDateTime Datetime;

	public void execRules() throws FileNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub

		initiateDBConn();

		numrules = getnumrules();

		System.out.println("Number of Active rules is: " + numrules);

		createRuleList();
		
		LeftRule[] myLR = new LeftRule[numrules]; // creates an array of the LR object which has all the logic for the Left Rules
		
		int left_sub_count;
		
		String SQL_out = "";
		
		for(int i=0; i<myLR.length; i++)
		{
			myLR[i] = new LeftRule();
			myLR[i].setRuleID(RuleList.get(i));
			SQL = myLR[i].getSQL_subrulecount();
			left_sub_count = myconn.execSQL_returnint(SQL);
			myLR[i].setLeft_sub_count(left_sub_count);
			
			// This for loop is for each sub rule within the rule
			// End goal is to get the Left SQL for that rule
			for (int j = 1; j <= left_sub_count; ++j){

				if ((j > 1) && (left_sub_count > 1)){
					SQL_out = SQL_out + "";
				}
				
				SQL = myLR[i].getLeftRuleTypeID(j);
				myLR[i].setLeftRuleTypeID(myconn.execSQL_returnint(SQL));
				SQL_out = SQL_out + myLR[i].getRuleSQL();
				System.out.println("SQL_out for Rule: " + RuleList.get(i) + " is: " + SQL_out);
			}
		}
		
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
