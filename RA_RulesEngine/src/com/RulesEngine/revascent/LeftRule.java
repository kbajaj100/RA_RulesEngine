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
	
}
