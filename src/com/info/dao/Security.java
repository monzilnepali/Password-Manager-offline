package com.info.dao;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class Security {

	//define algorithm for encryption
		private static final String ALGO="AES";
		private byte[] keyvalue;
		
		public Security(String  key) throws NoSuchAlgorithmException{
			keyvalue=key.getBytes();
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			keyvalue = sha.digest(keyvalue);
			//System.out.println("key value"+keyvalue);
			
		}
	    	private  Key generateKey(){
			Key key=new SecretKeySpec(keyvalue,ALGO);
			return key;
		}
		public String encrypt(String data) throws Exception{
			Key key=generateKey();
			Cipher c=Cipher.getInstance(ALGO);
			c.init(Cipher.ENCRYPT_MODE, key);
			byte[] encVal=c.doFinal(data.getBytes());
			String encryptedValue=new BASE64Encoder().encode(encVal);
			return encryptedValue ;
			
		}
		public String decrypt(String encrptedData) throws Exception{
			Key key=generateKey();
			Cipher c=Cipher.getInstance(ALGO);
			c.init(Cipher.DECRYPT_MODE, key);
			byte[] decodedValue=new BASE64Decoder().decodeBuffer(encrptedData);
			byte[] decValue=c.doFinal(decodedValue);
			String decryptedValue=new String(decValue);
			return decryptedValue;
		}
}
