package com.RulesEngine.revascent;

public class RightRule {

	String SQL;
	String SQL_out;
	String Claims;
	String Code;
	
	String Search_Type;
	int right_sub_count;
	int RuleID;
	int RightRuleTypeID;
	int occur_count;
	int RUN_ID;
	
	DBConn myConn;
	
	DBIndex myRRindex = new DBIndex();
	static DBConn myconn = new DBConn();
	static String dbUrl;
	
	
	
	public String getCode() {
		return Code;
	}
	public void setCode(String code) {
		Code = code;
	}
	public String getClaims() {
		return Claims;
	}
	public void setClaims(String claims) {
		Claims = claims;
	}
	public String getSearch_Type() {
		return Search_Type;
	}
	public void setSearch_Type(String search_Type) {
		Search_Type = search_Type;
	}
	public int getRight_sub_count() {
		return right_sub_count;
	}
	public void setRight_sub_count(int right_sub_count) {
		this.right_sub_count = right_sub_count;
	}
	public int getRuleID() {
		return RuleID;
	}
	public void setRuleID(int ruleID) {
		RuleID = ruleID;
	}
	public int getRightRuleTypeID() {
		return RightRuleTypeID;
	}
	public void setRightRuleTypeID(int rightRuleTypeID) {
		RightRuleTypeID = rightRuleTypeID;
	}
	public int getOccur_count() {
		return occur_count;
	}
	public void setOccur_count(int occur_count) {
		this.occur_count = occur_count;
	}
	public int getRUN_ID() {
		return RUN_ID;
	}
	public void setRUN_ID(int rUN_ID) {
		RUN_ID = rUN_ID;
	}
	
	public String getSQL_subrulecount(){
		
		SQL = "select count(Rule_Sub_ID) count " +
			  "from " + myRRindex.getRS() + " " +
			  "where Rule_ID = " + RuleID;
		
		return SQL;
	}
	
	public String getSQL_RightRuleTypeID(int j) {
		// TODO Auto-generated method stub
		
		SQL = "select Right_Rule_Type_ID count " +
			  "from " + myRRindex.getRS() + " " +
			  "where Rule_ID = " + RuleID + " " +
			  "and Rule_Sub_ID = " + j;
		
		return SQL;
	}
	
	public String getSQL_searchtype(int j) {
		// TODO Auto-generated method stub
		SQL = "select Search_Type code " +
			  "from " + myRRindex.getRS() + " " +
			  "where Rule_ID = " + RuleID + " " +
			  "and Rule_Sub_ID = " + j;
		
		return SQL;
	}
	
	public String getSQL_Rule(int j, String claims) {
		// TODO Auto-generated method stub
		
		if (RightRuleTypeID == 1)
			SQL = getSQL_Rule_RT1(j, claims);
		else if (RightRuleTypeID == 2)
			SQL = getSQL_Rule_RT2(j, claims);
		else if (RightRuleTypeID == 3)
			SQL = getSQL_Rule_RT3(j, claims);
		else if (RightRuleTypeID == 10)
			SQL = getSQL_Rule_RT10(j, claims);
				
		return SQL;
	}
	
	private String getSQL_Rule_RT10(int j, String claims) {
		// TODO Auto-generated method stub
		SQL = "select o11.CLM_ID, '" + Code + "'," + RuleID + "," + j + "," + RUN_ID + " " +
			  "from " +
			  "(select distinct a11.CLM_ID " +
			  "from " + myRRindex.getClaims_Table() + " a11 " +
			  "where a11.CLM_ID in (" +
			  claims + ") and " + 
			  "a11.CLM_ID not in " +
			  "( " +  
			  "select CLM_ID " +
			  "from " + myRRindex.getClaims_Table() + " " +
			  "where CPT_CODE in " + 
			  "(select Rule_Sub_Primary_Code1 " + 
			  "from " + myRRindex.getRS() + " " +
			  "where Rule_ID = " + RuleID + " " + 
			  "and Rule_Sub_ID = " + j + ")" +
			  "and CLM_ID in (" + claims + 
			  "))) o11";
		
		System.out.println(SQL);
		return SQL;
	}
	private String getSQL_Rule_RT3(int j, String claims) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String getSQL_Rule_RT2(int j, String claims) {
		// TODO Auto-generated method stub
		
		SQL = "select a11.CLM_ID, '" + Code + "'," + RuleID + "," + j + "," + RUN_ID + " " +
			  "from " + 	
			  "(select distinct CLM_ID " +
			  "from " + myRRindex.getClaims_Table() + ") a11 " +
			  "join " +
			  "(select CLM_ID " +
			  "from " + myRRindex.getClaims_Table() + " " +
			  "where CPT_CODE in " + 
			  "(select Rule_Sub_Primary_Code1 " + 
			  "from " + myRRindex.getRS() + " " + 
			  "where Rule_ID = " + RuleID + " " + 
			  "and Rule_Sub_ID = " + j + ")) a12 on " +
			  "(a11.CLM_ID = a12.CLM_ID) " +
			  "where a11.CLM_ID in (" +
			  claims + ") and " + 
			  "a11.CLM_ID not in " +
			  "(select distinct CLM_ID " +
			  "from " + myRRindex.getClaims_Table() + " " +
			  "where CPT_CODE in " + 
			  "(select Missing_Value " +
			  "from " + myRRindex.getRS() + " " +  
			  "where Rule_ID = " + RuleID + " " + 
			  "and Rule_Sub_ID = " + j + "))";
		
		return SQL;
	}
	
	private String getSQL_Rule_RT1(int j, String claims) {
		
						//CLM_ID, CPT_CODE, 	RULE_ID, SUB_RULE_ID, RUN_ID
		SQL = "select o11.CLM_ID, '" + Code +"',"+ RuleID + ","+ j + ","+ RUN_ID + " " + 
			  "from (" +		
			  "select a11.CLM_ID " + 
			  "from " + myRRindex.getClaims_Table() + " a11 " +
			  "where a11.CPT_Code in ('" + Code +
			  "') and CLM_ID in (" +
			  claims + ") " +
			  "group by a11.CLM_ID " +
			  "having COUNT(a11.CPT_CODE) = 1) o11";
		
		System.out.println(SQL);
		return SQL;
	}
	
	public String getSQL_code(int j) {
		// TODO Auto-generated method stub
		SQL = "select Missing_Value code " +
			  "from " + myRRindex.getRS() + " " + 
			  "where Rule_ID = " + RuleID + " " +
			  "and Rule_Sub_ID = " + j;
		
		//System.out.println(SQL);
		return SQL;
	}

	
}
