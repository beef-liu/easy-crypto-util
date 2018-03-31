package com.beef.util;

import java.io.UnsupportedEncodingException;

/**
 * @author XingGu Liu
 */
public class ByteArrayUtil {

    public static void setValueToByteArrayLittleEndian(short val,
                                                       byte[] byteArray, int offset) {
        byteArray[offset] = (byte) (val & 0x00FF);
        byteArray[offset + 1] = (byte) ((val >> 8) & 0x00FF);
    }

    public static void setValueToByteArrayBigEndian(short val,
                                                    byte[] byteArray, int offset) {
        byteArray[offset + 1] = (byte) (val & 0x00FF);
        byteArray[offset] = (byte) ((val >> 8) & 0x00FF);
    }

    public static void setValueToByteArrayLittleEndian(int val,
                                                       byte[] byteArray, int offset) {
        byteArray[offset] = (byte) (val & 0xFF);
        byteArray[offset + 1] = (byte) ((val >> 8) & 0xFF);
        byteArray[offset + 2] = (byte) ((val >> 16) & 0xFF);
        byteArray[offset + 3] = (byte) ((val >> 24) & 0xFF);
    }

    public static void setValueToByteArrayBigEndian(int val,
                                                    byte[] byteArray, int offset) {
        byteArray[offset + 3] = (byte) (val & 0xFF);
        byteArray[offset + 2] = (byte) ((val >> 8) & 0xFF);
        byteArray[offset + 1] = (byte) ((val >> 16) & 0xFF);
        byteArray[offset] = (byte) ((val >> 24) & 0xFF);
    }

    public static void setValueToByteArrayLittleEndian(long val,
                                                       byte[] byteArray, int offset) {
        byteArray[offset] = (byte) (val & 0xFFL);
        byteArray[offset + 1] = (byte) ((val >> 8) & 0xFFL);
        byteArray[offset + 2] = (byte) ((val >> 16) & 0xFFL);
        byteArray[offset + 3] = (byte) ((val >> 24) & 0xFFL);
        byteArray[offset + 4] = (byte) ((val >> 32) & 0xFFL);
        byteArray[offset + 5] = (byte) ((val >> 40) & 0xFFL);
        byteArray[offset + 6] = (byte) ((val >> 48) & 0xFFL);
        byteArray[offset + 7] = (byte) ((val >> 56) & 0xFFL);
    }

    public static void setValueToByteArrayBigEndian(long val,
                                                    byte[] byteArray, int offset) {
        byteArray[offset + 7] = (byte) (val & 0xFFL);
        byteArray[offset + 6] = (byte) ((val >> 8) & 0xFFL);
        byteArray[offset + 5] = (byte) ((val >> 16) & 0xFFL);
        byteArray[offset + 4] = (byte) ((val >> 24) & 0xFFL);
        byteArray[offset + 3] = (byte) ((val >> 32) & 0xFFL);
        byteArray[offset + 2] = (byte) ((val >> 40) & 0xFFL);
        byteArray[offset + 1] = (byte) ((val >> 48) & 0xFFL);
        byteArray[offset] = (byte) ((val >> 56) & 0xFFL);
    }

    public static void setStringToByteArray(
            String val, String encoding, int lenToSet,
            byte[] byteArray, int offset)
            throws UnsupportedEncodingException {

        byte[] bytesStr = val.getBytes(encoding);
        if (bytesStr.length < lenToSet) {
            int index = offset;
            for (int i = 0; i < bytesStr.length; i++) {
                byteArray[index] = bytesStr[i];
                index++;
            }
            int lenOfEmpty = lenToSet - bytesStr.length;
            for (int i = 0; i < lenOfEmpty; i++) {
                byteArray[index] = 0;
                index++;
            }
        } else {
            int index = offset;
            for (int i = 0; i < lenToSet; i++) {
                byteArray[index] = bytesStr[i];
                index++;
            }
        }
    }

    public static short getShortValueFromByteArrayLittleEndian(
            byte[] byteArray, int offset) {
        short val = 0;
        val = (short) (
                ((byteArray[offset] << 8 >> 8) & 0xFF)
                        + ((byteArray[offset + 1] << 8) & 0xFF00)
        );
        return val;
    }

    public static short getShortValueFromByteArrayBigEndian(
            byte[] byteArray, int offset) {
        short val = 0;
        val = (short) (
                ((byteArray[offset + 1] << 8 >> 8) & 0xFF)
                        + ((byteArray[offset] << 8) & 0xFF00)
        );
        return val;
    }

    public static int getIntValueFromByteArrayLittleEndian(
            byte[] byteArray, int offset) {
        int val = 0;
        val = ((byteArray[offset] << 8 >> 8) & 0xFF)
                + ((byteArray[offset + 1] << 8) & 0xFF00)
                + ((byteArray[offset + 2] << 16) & 0xFF0000)
                + ((byteArray[offset + 3] << 24) & 0xFF000000)
        ;
        return val;
    }

    public static int getIntValueFromByteArrayBigEndian(
            byte[] byteArray, int offset) {
        int val = 0;
        val = ((byteArray[offset + 3] << 8 >> 8) & 0xFF)
                + ((byteArray[offset + 2] << 8) & 0xFF00)
                + ((byteArray[offset + 1] << 16) & 0xFF0000)
                + ((byteArray[offset] << 24) & 0xFF000000)
        ;
        return val;
    }

    public static long getLongValueFromByteArrayLittleEndian(
            byte[] byteArray, int offset) {
        long val = 0;
        val = ((((long) byteArray[offset]) << 8 >> 8) & 0xFFL)
                + ((((long) byteArray[offset + 1]) << 8) & 0xFF00L)
                + ((((long) byteArray[offset + 2]) << 16) & 0xFF0000L)
                + ((((long) byteArray[offset + 3]) << 24) & 0xFF000000L)
                + ((((long) byteArray[offset + 4]) << 32) & 0xFF00000000L)
                + ((((long) byteArray[offset + 5]) << 40) & 0xFF0000000000L)
                + ((((long) byteArray[offset + 6]) << 48) & 0xFF000000000000L)
                + ((((long) byteArray[offset + 7]) << 56) & 0xFF00000000000000L)
        ;
        return val;
    }

    public static long getLongValueFromByteArrayBigEndian(
            byte[] byteArray, int offset) {
        long val = 0;
        val = ((((long) byteArray[offset + 7]) << 8 >> 8) & 0xFFL)
                + ((((long) byteArray[offset + 6]) << 8) & 0xFF00L)
                + ((((long) byteArray[offset + 5]) << 16) & 0xFF0000L)
                + ((((long) byteArray[offset + 4]) << 24) & 0xFF000000L)
                + ((((long) byteArray[offset + 3]) << 32) & 0xFF00000000L)
                + ((((long) byteArray[offset + 2]) << 40) & 0xFF0000000000L)
                + ((((long) byteArray[offset + 1]) << 48) & 0xFF000000000000L)
                + ((((long) byteArray[offset]) << 56) & 0xFF00000000000000L)
        ;
        return val;
    }

    public static String getStringFromByteArray(
            String encoding, int lenToGet,
            byte[] byteArray, int offset)
            throws UnsupportedEncodingException {
        String val = new String(byteArray, offset, lenToGet, encoding);
        return val;
    }

    public static void copyByteArray(byte[] srcBytes, int srcOffset,
                                     byte[] destBytes, int destOffset, int len) {
		/*
		int srcIndex = srcOffset;
		int destIndex = destOffset;
		
		for(int i = 0; i < len; i++) {
			
			destBytes[destIndex] = srcBytes[srcIndex];
			
			srcIndex++;
			destIndex++;
		}
		*/
        System.arraycopy(srcBytes, srcOffset, destBytes, destOffset, len);
    }

    public static void moveUpData(byte[] byteArray,
                                  int srcOffset, int destOffset, int len) {
		/*
		for(int i = 0; i < len; i++) {
			byteArray[destOffset + i] =
				byteArray[srcOffset + i];
				
		}
		*/
        System.arraycopy(byteArray, srcOffset, byteArray, destOffset, len);
    }

    public static void moveDownData(byte[] byteArray,
                                    int srcOffset, int destOffset, int len) {
		/*
		for(int i = len - 1; i >= 0; i--) {
			byteArray[destOffset + i] =
				byteArray[srcOffset + i];
		}
		*/
        System.arraycopy(byteArray, srcOffset, byteArray, destOffset, len);
    }

    public static boolean equalByteArray(byte[] byteArray1, int offset1,
                                         byte[] byteArray2, int offset2, int len) {
        int index1 = offset1;
        int index2 = offset2;

        for (int i = 0; i < len; i++) {
            if (byteArray1[index1] != byteArray2[index2]) {
                return false;
            }

            index1++;
            index2++;
        }

        return true;
    }

    public static int searchByteArray(
            byte[] byteArray, int startIndex, int len,
            byte[] byteArrayPattern, int startIndexOfPattern, int patternLen) {
        int endIndex = startIndex + len - 1;

        for (int index1 = startIndex; index1 <= endIndex; index1++) {
            if (equalByteArray(byteArray, index1, byteArrayPattern, startIndexOfPattern, patternLen)) {
                return index1;
            }
        }

        return -1;
    }

    public static boolean isAllByteZero(byte[] byteArray, int offset, int len) {
        int endIndex = offset + len;
        for (int i = offset; i < endIndex; i++) {
            if (byteArray[i] != 0) {
                return false;
            }
        }

        return true;
    }

    public static void clearByteArray(byte[] byteArray, int offset, int len) {
        int endIndex = offset + len;
        for (int i = offset; i < endIndex; i++) {
            byteArray[i] = 0;
        }
    }
}
