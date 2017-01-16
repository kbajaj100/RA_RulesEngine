package com.RulesEngine.revascent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class mainClass {

	public static void main(String[] args) throws FileNotFoundException, IOException, SQLException {
		// TODO Auto-generated method stub

		RARE myRare = new RARE();
		
		myRare.execRules();
		
	}

}
