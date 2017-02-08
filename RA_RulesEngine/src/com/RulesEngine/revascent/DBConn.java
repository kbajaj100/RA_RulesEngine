package com.RulesEngine.revascent;

import java.util.Properties;
import javax.sql.rowset.CachedRowSet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.sql.RowSet;
import com.sun.rowset.CachedRowSetImpl;

public class DBConn {

	private String dbName = "";
	private String user = "";
	private String pass = "";
	private String dbUrl = "";
	private String enc_status;
	//private String SQL = ""; 
	
	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs= null;
	private CachedRowSetImpl crs;
	
	
	public void setDBConn(String PropFileLocation) throws Exception{
					
			//1. Load the properties file
			Properties props = new Properties();
			props.load(new FileInputStream(PropFileLocation)); 
			
			//2. Read the props
			user = props.getProperty("user");
			pass = props.getProperty("password");
			dbName = props.getProperty("dbName");
			dbName = props.getProperty("dbName");
			enc_status = props.getProperty("encrypted");
			
			DBEncryptor myenc = new DBEncryptor("1234567891011121", enc_status);
			
			if (enc_status.equals("1")){	// new password
				dbUrl = props.getProperty("dbUrl") +  ";databaseName="+ dbName + ";user=" + user + ";password=" + pass;
				myenc.setEnc(pass);
				pass = myenc.getEnc();
				//Write the password back to the props file and change enc value to 0
			}
			else { // old password (encrypted)
				myenc.setDec(pass);
				pass = myenc.getDec();
				dbUrl = props.getProperty("dbUrl") +  ";databaseName="+ dbName + ";user=" + user + ";password=" + pass;
			}//System.out.println(dbUrl);
			
	}

	
	/*private void encryptandstore() {
		// TODO Auto-generated method stub
		
		System.out.println("Encrypted is: " + enc_status);
		
		byte[] input;
		byte[] keybytes = "12345678".getBytes();
		byte[] ivBytes = "input123".getBytes();
		
		SecretKeySpec key = new SecretKeySpec(keybytes,"DES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes); 
		Cipher cipher;
		byte[] cipherText;
		int ctLength;
		String Encryptedpwd;
		
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BounceCastleProvider());
			input = pass.getBytes();
			//SecretKeySpec mykey = new SecretKeySpec(keybytes, "DES");
			cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
			
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
			cipherText = new byte[cipher.getOutputSize(input.length)];
			
			ctLength = cipher.update(input, 0, input.length, cipherText,0);
			ctLength += cipher.doFinal(cipherText, ctLength);
			
			Encryptedpwd = new String(cipherText);
			System.out.println("Encrypted pwd is: " + new String(cipherText));
		}

		catch (Exception ex){

		}

	}

	private void decryptpass() {
		// TODO Auto-generated method stub
		//StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
		
		//textEncryptor.setPassword(pass);
		
		//pass = textEncryptor.encrypt(pass);
		System.out.println(pass);
		//pass = textEncryptor.decrypt(pass);
		
		//System.out.println(pass);
	}*/

	public String getdbName(){
		return dbName;
	}
	
	public String getuser(){
		return user;
	}
	
	public String getdbUrl(){
		//System.out.println(dbUrl);
		return dbUrl;
	}
	
	/*public String getpass(){
		return pass;
	}*/
	
	public int execSQL_returnint(String SQL){
		
		int count = 0;
		
		try {
			//Step 1. Connection to the db
			conn = DriverManager.getConnection(dbUrl);
		
			// Create statement object
			stmt = conn.createStatement();
		
			// 3. Execute SQL query
			rs = stmt.executeQuery(SQL);
			
			//4. Process result set
			while (rs.next()){
				
				count = rs.getInt("count");
				//System.out.println(count);
				return count;
			}
		}
		
		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			//close(myConn, myStmt, myRS);
			if (rs   != null) try { rs.close();   } catch(Exception e) {}
			if (stmt != null) try { stmt.close(); } catch(Exception e) {}
			if (conn != null) try { conn.close(); } catch(Exception e) {}
		}
		
		return count;
	}

	public String execSQL_returnString(String SQL)
	{
		String code = "";
		
		try {
			//Step 1. Connection to the db
			conn = DriverManager.getConnection(dbUrl);
		
			// Create statement object
			stmt = conn.createStatement();
		
			// 3. Execute SQL query
			rs = stmt.executeQuery(SQL);
			
			//4. Process result set
			while (rs.next()){
				
				code = rs.getString("code");
				//System.out.println(count);
				return code;
			}
		}
		
		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			//close(myConn, myStmt, myRS);
			if (rs   != null) try { rs.close();   } catch(Exception e) {}
			if (stmt != null) try { stmt.close(); } catch(Exception e) {}
			if (conn != null) try { conn.close(); } catch(Exception e) {}
		}
		
		return code;
		
	}

	public int execSQL(String SQL){
		
		try {
			//Step 1. Connection to the db
			conn = DriverManager.getConnection(dbUrl);
		
			// Create statement object
			stmt = conn.createStatement();
		
			// 3. Execute SQL query
			stmt.executeUpdate(SQL);
			
		}
		
		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public boolean execSQL_crs(String SQL)
	{
		
		try {
			//Step 1. Connection to the db
			conn = DriverManager.getConnection(dbUrl);
		
			// Create statement object
			stmt = conn.createStatement();
		
			// 3. Execute SQL query
			rs = stmt.executeQuery(SQL);
			
			// create CachedRowSet and populate
	        crs = new CachedRowSetImpl();
	        crs.populate(rs);
		}
		
		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			//close(myConn, myStmt, myRS);
			if (rs   != null) try { rs.close();   } catch(Exception e) {}
			if (stmt != null) try { stmt.close(); } catch(Exception e) {}
			if (conn != null) try { conn.close(); } catch(Exception e) {}
		}
		
		return true;
		
	}
	
	public CachedRowSetImpl getRowSet() {
	      return crs;
	}
	
}


