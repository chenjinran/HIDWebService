package com.birn.restful;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

public final class appUtil {
	
	public static String getProperty(String key) throws IOException {
		InputStream input;
		Properties prop = new Properties(); 
		String strProperty =  "";
		
		try {
			
			input = dbUtil.class.getClassLoader().getResourceAsStream("app.properties");			
			prop.load(input);
			strProperty = prop.getProperty(key);			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strProperty;
	}
		
	static Cipher cipher;
	
	public static String encrypt(String plainText)
			throws Exception {
		byte[] plainTextByte = plainText.getBytes("UTF-8");
		String encryptedText = DatatypeConverter.printBase64Binary(plainTextByte);
		return encryptedText;
	}
	
	public static String decrypt(String encryptedText)
			throws Exception {
	
		byte[] decryptedText = DatatypeConverter.parseBase64Binary(encryptedText);
		return new String(decryptedText, "UTF-8");
	}
		
}
