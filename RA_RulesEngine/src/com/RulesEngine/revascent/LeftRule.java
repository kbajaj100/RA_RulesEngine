package com.RulesEngine.revascent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LeftRule {

	String SQL;
	String SQL_out;
	String Claims;
	int left_sub_count;
	int RuleID;
	int LeftRuleTypeID;
	
	DBIndex myLRindex = new DBIndex();
	static DBConn myconn = new DBConn();
	static String dbUrl;
	
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
		System.out.println("Number of Sub Rules for " + RuleID + " is: " + this.left_sub_count);
	}

	public String getSQL_subrulecount() {
		// TODO Auto-generated method stub
		
		SQL = "select COUNT(distinct Left_Sub_Rule_ID) count " + 
				"from " + myLRindex.getRS_Left() + " " + 
				"where Rule_ID = " + RuleID;
		return SQL;
	}

	public void execLR(int ruleID) throws FileNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
	
		System.out.println("RuleID in LR is: " + ruleID);
		
	}

	public String getLeftRuleTypeID(int j) {
		// TODO Auto-generated method stub
		SQL = "select distinct Left_Rule_Type_ID count " + 
			  "from " + myLRindex.getRS_Left() + " " + 
			  "where Rule_ID = " + RuleID + " " +
			  "and Left_Sub_Rule_ID = " + j;
		
		return SQL;
	}	
	
	public void setLeftRuleTypeID(int j) {
		LeftRuleTypeID = j;	
		System.out.println("Left Rule Type for RuleID: " + RuleID + " is: " + LeftRuleTypeID);
	}

	public String getRuleSQL(int j) {
		
		// j is the left sub counter, not the count
		//Get the LeftRuleTypeID
		int lefttype;
		lefttype = LeftRuleTypeID;
		
		if (lefttype == 1) 
		{
			SQL = 	"select distinct CLM_ID CLM_ID " + 
					"from " + myLRindex.getClaims_Table() + " " +
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
					"having COUNT(CPT_CODE) = 2)";
		}
		else if (lefttype == 2)
		{
			SQL = 	"select distinct a11.CLM_ID CLM_ID " +
					"from " + 
					"(select CLM_ID " + 
					"from " + myLRindex.getClaims_Table() + " " + 
					"where CPT_CODE in " + 
					"(select Rule_Primary_Code " + 
					"from " + myLRindex.getRS_Left() + " " +
					"where Rule_ID = " + RuleID + " " +  
					"and Left_Sub_Rule_ID = " + j + " " +  
					"and Rule_Left_Line_ID = 1) " +
					"group by CLM_ID " +
					"having COUNT(CPT_CODE) = 1) a11 " +
					"join " +
					"(select CLM_ID " +
					"from " + myLRindex.getClaims_Table() + " " +
					"where CPT_CODE in " +
					"(select Rule_Primary_Code " +
					"from " + myLRindex.getRS_Left() + " " +
					"where Rule_ID = " + RuleID + " " +  
					"and Left_Sub_Rule_ID = " + j + " " +  
					"and Rule_Left_Line_ID = 2) " +
					"group by CLM_ID " +
					"having COUNT(CPT_CODE) = 1) a12 on " +
					"(a11.CLM_ID = a12.CLM_ID)"; 
			System.out.println(SQL);
		}
		else if (lefttype == 3){
			String SQL_in = "";
			//SQL_in = getSQLin_Left_RuleType3(ruleID, ruleType, ruleTypeNumber);
			//System.out.println(SQL);
			
			/*SQL = "select CLM_ID CLM_ID, count(CPT_SEQUENCE_ID) count " + 
				  "from " + myDBIndex.getClaims_Table() + " " + 
				  "where CPT_Code in " + SQL_in + " group by CLM_ID";
			*/
			
		}
		
		return SQL;
	}
}
