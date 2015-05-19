package com.beef.util.crypto;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.beef.util.HexUtil;

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
	
	/**
	 * 
	 * @param source
	 * @return hex of md5 
	 */
	public static String MD5Hex(byte[] source) {
		return HexUtil.toHexString(MD5(source));
	}

	/**
	 * 
	 * @param source
	 * @param charset
	 * @return hex of md5 
	 */
	public static String MD5Hex(String source, Charset charset) {
		return HexUtil.toHexString(MD5(source.getBytes(charset)));
	}
	
}
