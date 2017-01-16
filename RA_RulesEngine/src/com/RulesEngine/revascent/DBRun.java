package com.RulesEngine.revascent;

public class DBRun {

	DBIndex myindex = new DBIndex();
	
	public String getRunIDSQL() {
		// TODO Auto-generated method stub
		String SQL;
		
		SQL = "select (case when max(RUN_ID) is null then 1 else max(RUN_ID) end) count " +
			  "from " + myindex.getLeft_Flag();
		
		return SQL;
	}
	
	
}
