package com.beef.util.crypto;

import com.beef.util.HexUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author XingGu Liu
 */
public class MD5Util {
    private final static String VERSION = "20151221";

    static {
        System.out.println("MD5Util version:" + VERSION);
    }

    private static MessageDigest createMD() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] MD5(byte[] source) {
        final MessageDigest md = createMD();
        return md.digest(source);
    }

    /**
     * @param source
     * @return hex of md5
     */
    public static String MD5Hex(byte[] source) {
        return HexUtil.toHexString(MD5(source));
    }

    /**
     * @param source
     * @param charset
     * @return hex of md5
     */
    public static String MD5Hex(String source, Charset charset) {
        return HexUtil.toHexString(MD5(source.getBytes(charset)));
    }

    public static String MD5Sum(File file) throws IOException {
        final MessageDigest md = createMD();

        InputStream input = new FileInputStream(file);
        byte[] buff = new byte[2048];
        try {
            int readLen;
            while (true) {
                readLen = input.read(buff);

                if (readLen < 0) {
                    break;
                }

                if (readLen > 0) {
                    md.update(buff, 0, readLen);
                }
            }

            byte[] md5sum = md.digest();

            return HexUtil.toHexString(md5sum);
        } finally {
            input.close();
        }


    }

}
