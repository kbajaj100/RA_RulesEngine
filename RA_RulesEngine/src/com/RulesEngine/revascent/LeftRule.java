package com.RulesEngine.revascent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LeftRule {

	String SQL;
	String SQL_out;
	String Claims;
	
	String Search_Type;
	int left_sub_count;
	int RuleID;
	int LeftRuleTypeID;
	int occur_count;
	int RUN_ID;
	
	DBConn myConn;
	
	DBIndex myLRindex = new DBIndex();
	static DBConn myconn = new DBConn();
	static String dbUrl;
	
	public String getSearch_Type() {
	return Search_Type;
	}

	public void setSearch_Type(String search_Type) {
		Search_Type = search_Type;
	}

	public int getRUN_ID() {
		return RUN_ID;
	}

	public void setRUN_ID(int rUN_ID) {
		RUN_ID = rUN_ID;
	}

	public static DBConn getMyconn() {
		return myconn;
	}

	public static void setMyconn() throws Exception {
		//LeftRule.myconn = myconn;
		myconn = new DBConn();
		myconn.setDBConn("C:/Props/RulesEngine/DBprops.properties");
		
	}
	
	public int getoccur_count() {
		return occur_count;
	}

	public int getRuleID() {
		return RuleID;
	}

	public void setRuleID(int ruleID) {
		RuleID = ruleID;
	}

	public String getSQL_out() {
		return SQL_out;
	}
	
	public void setSQL_out(String sQL_out) {
		SQL_out = sQL_out;
	}
	
	public String getClaims() {
		return Claims;
	}
	
	public void setClaims(String claims) {
		Claims = claims;
	}

	public int getLeft_sub_count() {
		return left_sub_count;
	}

	public void setLeft_sub_count(int left_sub_count) {
		this.left_sub_count = left_sub_count;
		//System.out.println("Number of Sub Rules for " + RuleID + " is: " + this.left_sub_count);
	}

	
	public String getSQL_searchtype(int j) {
		
		SQL = "select distinct Search_Type code " +
			  "from " + myLRindex.RS_Left + " " + 
			  "where Rule_ID = " + RuleID + " " + 
			  "and Left_Sub_Rule_ID = " + j;
		
		//System.out.println(SQL);
		return SQL;
	}
	
	
	public String getSQL_subrulecount() {
		// TODO Auto-generated method stub
		
		SQL = "select COUNT(distinct Left_Sub_Rule_ID) count " + 
				"from " + myLRindex.getRS_Left() + " " + 
				"where Rule_ID = " + RuleID;
		return SQL;
	}

	public String getSQL_LeftRuleTypeID(int j) {
		// TODO Auto-generated method stub
		SQL = "select distinct Left_Rule_Type_ID count " + 
			  "from " + myLRindex.getRS_Left() + " " + 
			  "where Rule_ID = " + RuleID + " " +
			  "and Left_Sub_Rule_ID = " + j;
		
		return SQL;
	}	
	
	public void setLeftRuleTypeID(int j) throws Exception {
		LeftRuleTypeID = j;	
		
		if (j==3)
			setMyconn();
		
		//System.out.println("Left Rule Type for RuleID: " + RuleID + " is: " + LeftRuleTypeID);
	}

	public String getSQL_Rule(int j) {
		
		// j is the left sub counter, not the count
		//Get the LeftRuleTypeID
		
		if (LeftRuleTypeID == 1)
			SQL = getSQL_Rule_RT1(j," >= 2");
		else if (LeftRuleTypeID == 2)
			SQL = getSQL_Rule_RT2(j);
		else if (LeftRuleTypeID  == 3)
			SQL = getSQL_Rule_RT3(j);
		else if (LeftRuleTypeID  == 6)
				SQL = getSQL_Rule_RT3(j);
		else if (LeftRuleTypeID == 7)
			SQL = getSQL_Rule_RT1(j," = 2");
		else if (LeftRuleTypeID == 8)
			SQL = getSQL_Rule_RT1(j," = 1");
		
		return SQL;
	}

	private String getSQL_Rule_RT3(int j) {
		
		if (LeftRuleTypeID  == 3)
			SQL = "select CLM_ID, COUNT(distinct CPT_CODE) count1 ";
		else 
			SQL =  "select CLM_ID, COUNT(CPT_CODE) count1 ";
		
		SQL = SQL + 
			  "from " + myLRindex.getClaims_Table() + " " + 
			  "where CPT_CODE in " +  
			  "(" + 
			  "select Rule_Primary_Code " + 
			  "from " +  myLRindex.getRS_Left() + " " +
			  "where Rule_ID = " + RuleID + " " + 
			  "and Left_Sub_Rule_ID = " + j + " " +
			  ")" + " " + 
			  "group by CLM_ID";
		
		insertSQL_RT3(j);
		
		return SQL;
	}

	private String getSQL_Rule_RT2(int j) {
		// TODO Auto-generated method stub
		
		SQL = "select distinct a11.CLM_ID CLM_ID " +
		"from " + 
		"(select CLM_ID " + 
		"from " + myLRindex.getClaims_Table() + " " + 
		"where CPT_CODE in " + 
		"(select Rule_Primary_Code " + 
		"from " + myLRindex.getRS_Left() + " " +
		"where Rule_ID = " + RuleID + " " +  
		"and Left_Sub_Rule_ID = " + j + " " +  
		"and Rule_Left_Line_ID = 1) " +
		") a11 " +
		"join " +
		"(select CLM_ID " +
		"from " + myLRindex.getClaims_Table() + " " +
		"where CPT_CODE in " +
		"(select Rule_Primary_Code " +
		"from " + myLRindex.getRS_Left() + " " +
		"where Rule_ID = " + RuleID + " " +  
		"and Left_Sub_Rule_ID = " + j + " " +  
		"and Rule_Left_Line_ID = 2) " +
		") a12 on " +
		"(a11.CLM_ID = a12.CLM_ID)"; 
		
		System.out.println("Left SQL for Rule Type 2 is: " + SQL);
		return SQL;
	}

	private String getSQL_Rule_RT1(int j, String count_code) {
		
		if (LeftRuleTypeID == 1)
			SQL = 	"select distinct CLM_ID CLM_ID " ; 
		else if (LeftRuleTypeID == 7)
			SQL = "select distinct CLM_ID CLM_ID, 2 " ;
		else if (LeftRuleTypeID == 8)
			SQL = "select distinct CLM_ID CLM_ID, 1 " ;
					
		SQL = SQL +	"from " + myLRindex.getClaims_Table() + " " +
					"where CLM_ID in " +
					"(select CLM_ID " + 
					"from " + myLRindex.getClaims_Table() + " " + 
					"where CPT_CODE = " +  
					"(select distinct Rule_Primary_Code " + 
					"from " + myLRindex.getRS_Left() + " " +  
					"where Rule_ID = " + RuleID + " " +  
					"and Left_Sub_Rule_ID = " + j + " " +  
					") " + 
					"group by CLM_ID " + 
					"having COUNT(CPT_CODE) " + count_code + ")";
		
		return SQL;
	}

	public String getSQL_insert(int Claim_ID, int Run_ID) {
		// TODO Auto-generated method stub
		
		SQL = "insert into " + myLRindex.getLeft_Flag() + " " + 
			  "(Run_ID, CLM_ID, RULE_ID)" + " " +
			  "values(" + Run_ID + "," +  Claim_ID + "," + RuleID + ")";
		
		//System.out.println(SQL);
		return SQL;
	}

	public void insertSQL_RT3(int j) {
		// TODO Auto-generated method stub
		
		String SQL_occur;
		
		SQL_occur = "insert into " + myLRindex.getLeft_Occur() + " " + 
				  "(Run_ID, RULE_ID, CLM_ID, COUNT_OCCUR)" + " " +
				  "select " + RUN_ID + "," + RuleID +",a11.CLM_ID, a11.count1 " +
				  "from (" +  SQL + ") a11"; 
				  /*"select " + RUN_ID + "," + RuleID +",CLM_ID, COUNT(distinct CPT_CODE) count " + 
				  "from " + myLRindex.getClaims_Table() + " " + 
				  "where CPT_CODE in " +  
				  "(" + 
				  "select Rule_Primary_Code " + 
				  "from " +  myLRindex.getRS_Left() + " " +
				  "where Rule_ID = " + RuleID + " " + 
				  "and Left_Sub_Rule_ID = " + j + " " +
				  ")" + " " + 
				  "group by CLM_ID";*/  
			
		System.out.println(SQL_occur);
		myconn.execSQL(SQL_occur);
	}
	
}
