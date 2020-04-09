/**
 * 
 */
package com.scr.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

/**
 * @author vt1056
 *
 */
@Component
public class PasswordEncryption  {

	public String encode(String password) {
		try {			
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte[] bytes = password.getBytes("UTF-8");
			md.update(bytes, 0, bytes.length);
			bytes = md.digest();
			StringBuilder sb = new StringBuilder();
			sb.append("{").append("SHA").append("}");
			for (byte b : bytes) {
				sb.append(String.format("%02x", b));
			}
			String cryPwd = sb.toString();
			System.out.println("hash:::" + cryPwd);
			return cryPwd;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {			
			e.printStackTrace();
		}
		return password;
	}
public static void main(String args[]) {
	PasswordEncryption encryption = new PasswordEncryption();
	encryption.encode("venkat@123");
}
}
