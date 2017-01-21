package com.RulesEngine.revascent;

public class DBIndex {

	String Claims_Table = "rcmods.VW_CLAIM_CPT";
	String Flagged_Table = "rcmods.FACT_GIC_STG"; 
	String Left_Flag = "rcmods.FACT_GIC_STG_LEFT";
	String Left_Occur = "rcmods.FACT_GIC_OCCURENCE";
	String Right_3 = "rcmods.rt3_temp";
	
	String RS_Index = "rcmods.Rule_sheet_Index";
	String RS = "rcmods.Rule_Sheet";
	String RS_Left = "rcmods.Rule_Sheet_Left";

	
	public String getRight_3() {
		return Right_3;
	}

	public void setRight_3(String right_3Temp) {
		Right_3 = right_3Temp;
	}

	public String getLeft_Occur() {
		return Left_Occur;
	}

	public void setLeft_Occur(String left_Occur) {
		Left_Occur = left_Occur;
	}

	public String getLeft_Flag() {
		return Left_Flag;
	}

	public void setLeft_Flag(String left_Flag) {
		Left_Flag = left_Flag;
	}

	public String getClaims_Table() {
		return Claims_Table;
	}
	
	public void setClaims_Table(String claims_Table) {
		Claims_Table = claims_Table;
	}
	
	public String getFlagged_Table() {
		return Flagged_Table;
	}
	
	public void setFlagged_Table(String flagged_Table) {
		Flagged_Table = flagged_Table;
	}
	
	public String getRS_Index() {
		return RS_Index;
	}
	
	public void setRS_Index(String rS_Index) {
		RS_Index = rS_Index;
	}
	
	public String getRS() {
		return RS;
	}
	
	public void setRS(String rS) {
		RS = rS;
	}
	
	public String getRS_Left() {
		return RS_Left;
	}
	
	public void setRS_Left(String rS_Left) {
		RS_Left = rS_Left;
	}
	
}