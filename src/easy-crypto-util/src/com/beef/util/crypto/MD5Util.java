package com.beef.util.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author XingGu Liu
 *
 */
public class MD5Util {

	private static MessageDigest md = null;
	
	static {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public synchronized static byte[] MD5(byte[] source) {
		return md.digest(source);
	}
}
