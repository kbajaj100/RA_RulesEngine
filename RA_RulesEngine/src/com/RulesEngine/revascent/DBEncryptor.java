package com.RulesEngine.revascent;

import java.security.Key;
import java.util.logging.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class DBEncryptor {

	String enc;
	String dec;
	
	private static final String ALGO = "AES";
	private byte[] keyValue;
	private String status;

	public DBEncryptor (String key, String stat){
		keyValue = key.getBytes();
		System.out.println("keyvalue is: " + keyValue);
		status = stat;
	}
	
	public String getEnc() throws Exception {
		enc = Encrypt();
		System.out.println("Enc value is: " + enc);
		return enc;
	}

	public void setEnc(String enc1) {
		this.enc = enc1;
	}

	public String getDec() throws Exception {
		dec = Decrypt();
		System.out.println("Dec value is: " + dec);
		return dec;
	}

	public void setDec(String dec) {
		this.dec = dec;
	}

	public String Encrypt() throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(enc.getBytes());
		String encryptedvalue = new BASE64Encoder().encode(encVal);
		return encryptedvalue;
		
	}

	public String Decrypt() throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = new BASE64Decoder().decodeBuffer(dec);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedvalue = new String(decValue);
		return decryptedvalue;
	}
	
	private Key generateKey() {
		// TODO Auto-generated method stub
		Key key = new SecretKeySpec(keyValue, ALGO);
		return key;
	}
	
}
