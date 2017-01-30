package com.RulesEngine.revascent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.sun.rowset.CachedRowSetImpl;

public class RightRule {

	String SQL;
	String SQL_out;
	String Claims;
	String Code;
	String SQL_Claims;
	
	String Search_Type;
	int Search_type_count;
	int right_sub_count;
	int right_sub;
	int RuleID;
	int RightRuleTypeID;
	int occur_count;
	int RUN_ID;
	int Rule_Line_ID;
	int Sub_Line_Count;
	int Base_Rule_Type_ID;
	String Rel_Next;
	
	DBConn myConn;
	
	DBIndex myRRindex = new DBIndex();
	static DBConn myconn = new DBConn();
	static String dbUrl;
	
	
	public String get_SQL_Claims() {
		return SQL_Claims;
	}
	public void set_SQL_Claims() {
		SQL_Claims = "select CLM_ID " +
					 "from " + myRRindex.getLeft_Flag() + " " + 
					 "where Rule_ID =" + RuleID + " " +
					 "and RUN_ID = " + RUN_ID;	
	}
	public int getSearch_type_count() {
		return Search_type_count;
	}
	public void setSearch_type_count(int search_type_count) {
		Search_type_count = search_type_count;
	}
	public int getSub_Line_Count() {
		return Sub_Line_Count;
	}
	public void setSub_Line_Count(int sub_Line_Count) {
		Sub_Line_Count = sub_Line_Count;
	}
	public int getRight_sub() {
		return right_sub;
	}
	public void setRight_sub(int right_sub) {
		this.right_sub = right_sub;
	}
	public int getRule_Line_ID() {
		return Rule_Line_ID;
	}
	public void setRule_Line_ID(int rule_Line_ID) {
		Rule_Line_ID = rule_Line_ID;
	}
	public int getBase_Rule_Type_ID() {
		return Base_Rule_Type_ID;
	}
	public void setBase_Rule_Type_ID(int base_Rule_Type_ID) {
		Base_Rule_Type_ID = base_Rule_Type_ID;
	}
	public String getRel_Next() {
		return Rel_Next;
	}
	public void setRel_Next(String rel_Next) {
		Rel_Next = rel_Next;
	}
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
	
	
	private void init_dbconn() throws FileNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		myconn = new DBConn();
		myconn.setDBConn("C:/Props/RulesEngine/DBprops.properties");
	}
	
	public String getSQL_RelNext(int j, int line){
		
		SQL = "select REL_NEXT count " +
			  "from " + myRRindex.getRS() + " " + 
			  "where Rule_ID =" + RuleID + " " + 
			  "and Rule_Sub_ID = "  + j + " " +
			  "and Rule_line_ID = " + line;
		
		return SQL;
	}
	
	public String getSQL_Base_Rule(int j, int line){
		
		SQL = "select distinct Base_Rule_Type_ID count " +
			  "from " + myRRindex.getRS() + " " + 
			  "where Rule_ID =" + RuleID + " " + 
			  "and Rule_Sub_ID = "  + j + " " +
			  "and Rule_line_ID = " + line;
		
		return SQL;
	}
	
	public String getSQL_Rule_Line_Count(int j){
		
		SQL = "select count(Rule_Line_ID) count " +
			  "from " + myRRindex.getRS() + " " + 
			  "where Rule_ID =" + RuleID + " " + 
			  "and Rule_Sub_ID = "  + j;
			 
		return SQL;
	}
	public String getSQL_Rule_Line_ID(int j){
		
		SQL = "select min(Rule_Line_ID) count " +
			  "from " + myRRindex.getRS() + " " + 
			  "where Rule_ID =" + RuleID + " " + 
			  "and Rule_Sub_ID = "  + j;
			 
		return SQL;
	}
	
	public String getSQL_subrulecount(){
		
		SQL = "select count(distinct Rule_Sub_ID) count " +
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
	
	public String getSQL_searchtypecount(int j) {
		// TODO Auto-generated method stub
		SQL = "select count(distinct Search_Type) count " +
			  "from " + myRRindex.getRS() + " " +
			  "where Rule_ID = " + RuleID + " " +
			  "and Rule_Sub_ID = " + j;
		
		return SQL;
	}
	
	public String getSQL_code(int j, int line_number) {
		// TODO Auto-generated method stub
		SQL = "select Missing_Value code " +
			  "from " + myRRindex.getRS() + " " + 
			  "where Rule_ID = " + RuleID + " " +
			  "and Rule_Sub_ID = " + j + " " +
			  "and Rule_Line_ID = " + line_number;
		
		//System.out.println(SQL);
		return SQL;
	}
	
	public String getSQL_Rule(int j, String claims) throws FileNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		
		set_SQL_Claims();
		
		if (RightRuleTypeID == 1)
			SQL = getSQL_Rule_RT1(j, claims, Rule_Line_ID);
		else if (RightRuleTypeID == 2)
			SQL = getSQL_Rule_RT2(j, claims, Rule_Line_ID);
		else if (RightRuleTypeID == 3)
			SQL = getSQL_Rule_RT3(j, claims, Rule_Line_ID);
		else if (RightRuleTypeID == 5)
			SQL = getSQL_Rule_RT5(j, claims, Rule_Line_ID);
		else if (RightRuleTypeID == 10)
			SQL = getSQL_Rule_RT10(j, claims, Rule_Line_ID);
		else if (RightRuleTypeID == 11)
			SQL = getSQL_Rule_RT11(j, claims);
		else if (RightRuleTypeID == 12)
			SQL = getSQL_Rule_RT12(j, claims, Rule_Line_ID);
		else if (RightRuleTypeID == 15)
			SQL = getSQL_Rule_RT15(j, claims, Rule_Line_ID);
		
		return SQL;
	}
	
	private String getSQL_Rule_RT15(int j, String claims, int line) throws FileNotFoundException, IOException, SQLException {
		
		init_dbconn();
		
		SQL = getClaims_and_CountsRT3(j, claims, line);
		
		String SQL1 = "select distinct o11.CLM_ID from (" + SQL + ") o11 ";

		SQL = "select distinct CLM_ID, '' as Code," + RuleID + " as RuleID," + j + " as SubID," + RUN_ID + " as RunID " +
			  "from " + myRRindex.getLeft_Flag() + " " +
			  "where CLM_ID not in (" + SQL1 + ") " +
			  "and Run_ID = " + RUN_ID + " " +
			  "and Rule_ID = " + RuleID;
		
		System.out.println("Right Rule Type ID is: " + RightRuleTypeID);
		System.out.println(SQL);
		
		return SQL;
	}
	
	private String getSQL_Rule_RT12(int j, String claims, int rule_Line_ID) {
		
		SQL = "select distinct o11.CLM_ID, '" + Code + " ' as Code," + RuleID + " as RuleID," + j + " as SubID," + RUN_ID + " as RunID " + 
			  "from " + myRRindex.getClaims_Table() + " o11 " + 
			  "where o11.CLM_ID not in " + 
			  "(select distinct a11.CLM_ID " + 
			  "from " + myRRindex.getClaims_Table() + " a11 " + 
			  "where (a11.CPT_CODE in " + 
			  "(select Rule_Sub_Primary_Code1 " + 
			  "from " + myRRindex.getRS() + " " + 
			  "where Rule_ID = " + RuleID + " " + 
			  "and Rule_Sub_ID = " + j + " " +
			  "and Rule_Line_ID = " + rule_Line_ID + " " + 
			  ")" + " " + 
			  "or CPT_CODE in " + 
			  "(select Missing_Value " + 
			  "from rcmods.Rule_Sheet " + 
			  "where Rule_ID = " + RuleID + " " + 
			  "and Rule_Sub_ID = " + j + " " + 
			  "and Rule_Line_ID = " + rule_Line_ID  + " " +
			  ")) " +
			  "and CLM_ID in (" + SQL_Claims + ")) " +  
			  "and o11.CLM_ID in (" + SQL_Claims + ")";
		
		System.out.println(SQL);
		return SQL;
	}
	
	private String getSQL_Rule_RT11(int j, String claims) throws FileNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub
		
		init_dbconn();
		
		int base, checker = Rule_Line_ID;
		
		int Rel = myconn.execSQL_returnint(getSQL_RelNext(j, checker));;
		
		
		String SQL_11 = " select q" + checker + ".CLM_ID, q" + checker +".Code, q" + checker +".RuleID, q"+ checker + 
						".SubID, q" + checker + ".RunID from (";
		
		
		//checker is counter for line id
		for(; checker < (Sub_Line_Count + Rule_Line_ID); ++checker)
		{
			//get the base rule for this line
			base = myconn.execSQL_returnint(getSQL_Base_Rule(j, checker));
			
			System.out.println("base rule_id is: " + base);
			
			if (base == 1)
				SQL = getSQL_Rule_RT1(j, claims, checker);
			else if (base == 2)
				SQL = getSQL_Rule_RT2(j, claims, checker);
			else if (base == 3)
				SQL = getSQL_Rule_RT3(j, claims, checker);
			else if (base == 5)
				SQL = getSQL_Rule_RT5(j, claims, checker);
			else if (base == 10)
				SQL = getSQL_Rule_RT10(j, claims, checker);
			else if (base == 12)
				SQL = getSQL_Rule_RT12(j, claims, checker);
			
			// Rel is currently 'AND'. The logic will need to be re-thought/ changed to allow 'OR' 
			if (Rel ==1){
				System.out.println("REL_NEXT is: " + Rel);
				if (checker == Rule_Line_ID)
					SQL_11 = SQL_11 + SQL + ") q" + checker ;
				else 
					SQL_11 = SQL_11 + " join (" + SQL + ") q" + checker + 
							 " on (q" + checker + ".CLM_ID = q" + (checker-1) + ".CLM_ID) ";
			}
			System.out.println("SQL11 is: " + SQL_11);	
		}

		return SQL_11;
	}
	
	private String getSQL_Rule_RT10(int j, String claims, int line) {
		// TODO Auto-generated method stub
		SQL = "select o11.CLM_ID, '" + Code + " ' as Code," + RuleID + " as RuleID," + j + " as SubID," + RUN_ID + " as RunID " +
			  "from " +
			  "(select distinct a11.CLM_ID " +
			  "from " + myRRindex.getClaims_Table() + " a11 " +
			  "where a11.CLM_ID in (" +
			  SQL_Claims + ") and " + 
			  "a11.CLM_ID not in " +
			  "( " +  
			  "select CLM_ID " +
			  "from " + myRRindex.getClaims_Table() + " " +
			  "where CPT_CODE in " + 
			  "(select Rule_Sub_Primary_Code1 " + 
			  "from " + myRRindex.getRS() + " " +
			  "where Rule_ID = " + RuleID + " " + 
			  "and Rule_Sub_ID = " + j + ")" +
			  "and CLM_ID in (" + SQL_Claims + 
			  "))) o11";
		
		System.out.println(SQL);
		return SQL;
	}
	
	private String getSQL_Rule_RT5(int j, String claims, int line) {
		// TODO Auto-generated method stub
		return SQL;
	}
	
	private String getSQL_Rule_RT3(int j, String claims, int line) throws FileNotFoundException, IOException, SQLException {
		
		init_dbconn();
		SQL = "create table " + myRRindex.getRight_3() + " (CLM_ID int, CPT_COUNT int, Rule_ID int)";
		myconn.execSQL(SQL);

		String SQL = getClaims_and_CountsRT3(j, claims, line);
		
		SQL = "insert into " + myRRindex.getRight_3() + 
			  " (CLM_ID, CPT_COUNT, Rule_ID)" + " " + SQL;
		
		System.out.println("insert for RT3 temp is: " + SQL);
		myconn.execSQL(SQL);
		
		SQL = "select a11.CLM_ID, '' as Code," + RuleID + " as RuleID," + j + " as SubID," + RUN_ID + " as RunID " +
			  "from " + myRRindex.getRight_3() + " a11 " +
			  "join " + myRRindex.getLeft_Occur() + " a12 on " +
			  "(a11.CLM_ID = a12.CLM_ID) " + 
			  "where RUN_ID = " + RUN_ID + " " +
			  "and a11.CPT_COUNT > a12.COUNT_OCCUR " + 
			  "and a12.Rule_ID = " + RuleID + " " + 
			  "and a12.Rule_ID = a11.Rule_ID" + " " +
			  "union " +
			  "select CLM_ID, '' as Code," + RuleID + " as RuleID," + j + " as SubID," + RUN_ID + " as RunID " +
			  "from " + myRRindex.getLeft_Occur() + " " +
			  "where Run_ID = " + RUN_ID + " " +
			  "and Rule_ID = " + RuleID + " " + 
			  "and CLM_ID not in " +
			  "(select distinct CLM_ID from " + myRRindex.getRight_3() + " where Rule_ID = " + RuleID + ") ";
			  
		System.out.println(SQL);
				
		return SQL;
	}
	
	private String getClaims_and_CountsRT3(int j, String claims, int line) throws FileNotFoundException, IOException, SQLException {
		
		// Get the claims and the counts
				String[] search_types = create_Searchtype_arrRT3(j);
				int min_line, line_count, rel;
				String SQL_clause = "(";
					
				// This will run for each search type
				for (int i = 0; i <Search_type_count; ++i){
					
					System.out.println("Search type count is: " + Search_type_count);
					// Get the min Rule Line ID for the first search type
					SQL = "select min(Rule_Line_ID) count " + 
						  "from " + myRRindex.getRS() + " " +  
						  "where Rule_ID =" + RuleID + " " + 
						  "and Rule_Sub_ID = " + j + " " + 
						  "and Search_Type = '" + search_types[i] + "'";
					
					min_line = myconn.execSQL_returnint(SQL);
					
					SQL = "select count(Rule_Line_ID) count " + 
							  "from " + myRRindex.getRS() + " " +  
							  "where Rule_ID =" + RuleID + " " + 
							  "and Rule_Sub_ID = " + j + " " + 
							  "and Search_Type = '" + search_types[i] + "'";			
					
					//Get the number of lines for that search type in the rule
					line_count = myconn.execSQL_returnint(SQL);
					
					System.out.println("Min Line is: " + min_line);
					System.out.println("Line count for the search code is: " + line_count);
					//SQL_clause = "(";
					
					for(int checker = min_line; checker < (min_line + line_count); ++checker){
					
						System.out.println("Line is: " + checker);
						System.out.println("Line count for the search code is: " + line_count);
						System.out.println("Search type is: " + search_types[i]);
						
						if (search_types[i].equals("C")){
							SQL_clause = SQL_clause + 
										 "CPT_CODE in ( " +
										 "select Rule_Sub_Primary_Code1 " +
										 "from " + myRRindex.getRS() + " " + 
										 "where Rule_ID = " + RuleID +" " + 
										 "and Rule_Sub_ID = " + j + " " +
										 "and Search_Type = " + "'C') ";
							checker = checker + line_count;
							System.out.println(SQL_clause);
						}
						else if (search_types[i].equals("L")){
							
							//get the search code
							SQL = "select Rule_Sub_Primary_Code1 code " +
									 "from " + myRRindex.getRS() + " " + 
									 "where Rule_ID = " + RuleID +" " + 
									 "and Rule_Sub_ID = " + j + " " +
									 "and Search_Type = " + "'L' "+
									 "and Rule_Line_ID = " + checker;
							
							String code = myconn.execSQL_returnString(SQL);
							System.out.println("Search type Code is: " + code);
							
							SQL_clause = SQL_clause + "CPT_CODE like ('" + code + "%') ";
							System.out.println(SQL_clause);
						}
						else if (search_types[i].equals("R")){
							
							int code_id1, code_id2;
							System.out.println("search type is: " + search_types[i]);
							
							SQL = "select CPT_CODE_ID count " +
								  "from " + myRRindex.getClaims_Table() + " a11 " + 
								  "join " + myRRindex.getRS() + " a12 on " +
								  "(a11.CPT_CODE = a12.Rule_Sub_Primary_Code1) " +
								  "where a12.Rule_ID = " + RuleID + " " + 
								  "and a12.Rule_Sub_ID = " + j + " " +
								  "and a12.Search_Type = " + "'R' "+
								  "and a12.Rule_Line_ID = " + checker;
							
							code_id1 = myconn.execSQL_returnint(SQL);

							SQL = 	  "select CPT_CODE_ID count " +
									  "from " + myRRindex.getClaims_Table() + " a11 " + 
									  "join " + myRRindex.getRS() + " a12 on " +
									  "(a11.CPT_CODE = a12.Missing_Value) " +
									  "where a12.Rule_ID = " + RuleID + " " + 
									  "and a12.Rule_Sub_ID = " + j + " " +
									  "and a12.Search_Type = " + "'R' "+
									  "and a12.Rule_Line_ID = " + checker;
							
							code_id2 = myconn.execSQL_returnint(SQL);
							
							SQL_clause = SQL_clause + "CPT_CODE_ID between " + code_id1 + " and " + code_id2;
							System.out.println(SQL_clause);
						}
						
						SQL =    "select REL_NEXT count " +
								 "from " + myRRindex.getRS() + " " + 
								 "where Rule_ID = " + RuleID +" " + 
								 "and Rule_Sub_ID = " + j + " " +
								 "and Rule_Line_ID = " + checker;
						
						rel = myconn.execSQL_returnint(SQL);
						System.out.println(rel);
						
						if (rel == 0)
							SQL_clause = SQL_clause + ") ";
						else if (rel == 1)
							SQL_clause = SQL_clause + " and ";
						else if (rel ==2)
							SQL_clause = SQL_clause + " or ";
						
						System.out.println(SQL_clause);
						
					}
					
				}
				
				SQL_clause = SQL_clause + "and CLM_ID in (" + SQL_Claims + ") group by CLM_ID"; 
				System.out.println("Final SQL_Clause is: " + SQL_clause);
						
				SQL = "select CLM_ID, count(CPT_CODE) count1," + RuleID + " as Rule_ID " + 
					  "from " + myRRindex.getClaims_Table() + " " +
					  "where " + SQL_clause;
				
				System.out.println(SQL);
								
				return SQL;

	}
	private String[] create_Searchtype_arrRT3(int j) throws FileNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub

		//init_dbconn();
		String[] search_types = new String[Search_type_count];
		
		SQL = "select distinct rtrim(Search_type) code " +
			  "from " + myRRindex.getRS() + " " + 
			  "where Rule_ID =" + RuleID + " " + 
			  "and Rule_Sub_ID = " + j + " " +
			  "order by rtrim(Search_type)";
	
		System.out.println(SQL);
		int i = 0;
		
		if(!myconn.execSQL_crs(SQL))
			System.exit(1);
		
	    try {

	    	CachedRowSetImpl crs = new CachedRowSetImpl();
	    	crs = myconn.getRowSet();

	    	while (crs.next()) {	    			
	    		search_types[i] = crs.getString(1);
	    		++i;
	    		//System.out.println("search type is: " + search_types[i]);
	    	}

	    } catch (SQLException se){
	    	se.printStackTrace();
	    }
		
		return search_types;
		
	}
	
	private String getSQL_Rule_RT2(int j, String claims, int line) {
		
		SQL = "select a11.CLM_ID, '" + Code + " ' as Code," + RuleID + " as RuleID," + j + " as SubID," + RUN_ID + " as RunID " +
			  //"select a11.CLM_ID, '" + Code + "'," + RuleID + "," + j + "," + RUN_ID + " " +
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
			  SQL_Claims + ") and " + 
			  "a11.CLM_ID not in " +
			  "(select distinct CLM_ID " +
			  "from " + myRRindex.getClaims_Table() + " " +
			  "where CPT_CODE in " + 
			  "(select Missing_Value " +
			  "from " + myRRindex.getRS() + " " +  
			  "where Rule_ID = " + RuleID + " " + 
			  "and Rule_Line_ID = " + line + " " +
			  "and Rule_Sub_ID = " + j + ")) ";
		
		return SQL;
	}
	
	private String getSQL_Rule_RT1(int j, String claims, int line) {
		
						//CLM_ID, CPT_CODE, 	RULE_ID, SUB_RULE_ID, RUN_ID
		SQL = "select o11.CLM_ID, '" + Code + " ' as Code," + RuleID + " as RuleID," + j + " as SubID," + RUN_ID + " as RunID " +
			  //"select o11.CLM_ID, '" + Code +"',"+ RuleID + ","+ j + ","+ RUN_ID + " " + 
			  "from (" +		
			  "select a11.CLM_ID " + 
			  "from " + myRRindex.getClaims_Table() + " a11 " +
			  "where a11.CPT_Code in (select Missing_Value " +
			  "from " + myRRindex.getRS() + " " +  
			  "where Rule_ID = " + RuleID + " " + 
			  "and Rule_Line_ID = " + line + " " +
			  "and Rule_Sub_ID = " + j + ") "+ 
			  "and CLM_ID in (" +
			  SQL_Claims + ") " +
			  "group by a11.CLM_ID " +
			  "having COUNT(a11.CPT_CODE) = 1) o11";
		
		System.out.println(SQL);
		return SQL;
	}

}
