package com.beef.util;

/**
 * @author XingGu Liu
 */
public class HexUtil {
    private final static String[] ByteHexArray = new String[] {
            "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0a", "0b", "0c", "0d", "0e", "0f",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1a", "1b", "1c", "1d", "1e", "1f",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2a", "2b", "2c", "2d", "2e", "2f",
            "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3a", "3b", "3c", "3d", "3e", "3f",
            "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4a", "4b", "4c", "4d", "4e", "4f",
            "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5a", "5b", "5c", "5d", "5e", "5f",
            "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6a", "6b", "6c", "6d", "6e", "6f",
            "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7a", "7b", "7c", "7d", "7e", "7f",
            "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8a", "8b", "8c", "8d", "8e", "8f",
            "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9a", "9b", "9c", "9d", "9e", "9f",
            "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", "a9", "aa", "ab", "ac", "ad", "ae", "af",
            "b0", "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9", "ba", "bb", "bc", "bd", "be", "bf",
            "c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "ca", "cb", "cc", "cd", "ce", "cf",
            "d0", "d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "da", "db", "dc", "dd", "de", "df",
            "e0", "e1", "e2", "e3", "e4", "e5", "e6", "e7", "e8", "e9", "ea", "eb", "ec", "ed", "ee", "ef",
            "f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "fa", "fb", "fc", "fd", "fe", "ff"
    };

    private final static String[] Zeros = {
            "",
            "0",
            "00",
            "000",
            "0000",
            "00000",
            "000000",
            "0000000",
            "00000000",
            "000000000",
            "0000000000",
            "00000000000",
            "000000000000",
            "0000000000000",
            "00000000000000",
            "000000000000000",
    };

    public static String toHexString(byte val) {
        int index = (val & 0xFF);
        return ByteHexArray[index];
    }

    public static String toHexString(byte[] val) {
        return toHexString(val, 0, val.length);
    }

    public static String toHexString(byte[] val, int offset, int length) {
        StringBuilder str = new StringBuilder();

        int end = offset + length;
        int index;
        for(int i = offset; i < end; i++) {
            //str.append(toHexString(val[i]));
            index = (val[i] & 0xFF);
            str.append(ByteHexArray[index]);
        }

        return str.toString();
    }

    public static String toHexString(short val) {
        String hex = Integer.toHexString(val);
        return Zeros[4 - hex.length()].concat(hex);
    }

    public static String toHexString(int val) {
        String hex = Integer.toHexString(val);
        return Zeros[8 - hex.length()].concat(hex);
    }

    public static String toHexString(long val) {
        String hex = Long.toHexString(val);
        return Zeros[16 - hex.length()].concat(hex);
    }

    public static byte[] parseHexString(String hexStr) {
        int byteLen = hexStr.length() / 2;
        byte[] buffer = new byte[byteLen];

        parseHexString(hexStr, buffer, 0);

        return buffer;
    }

    public static void parseHexString(String hexStr, byte[] destBuff, int offset) {
        int byteMax = offset + hexStr.length() / 2;
        int beginIndex = 0;
        for(int i = offset; i < byteMax; i++) {
            destBuff[i] = (byte)(
                    hexCharToByte(hexStr.charAt(beginIndex + 1))
                            | ((hexCharToByte(hexStr.charAt(beginIndex)) << 4) & 0xf0)
            );

            beginIndex += 2;
        }
    }

    public static byte hexCharToByte(char c) {
        if(c <= '9' && c >= '0') {
            return (byte)(c - '0');
        } else {
            if(c >= 'a' && c <= 'f') {
                return (byte)(c - 'a' + 10);
            } else if(c >= 'A' && c <= 'F') {
                return (byte)(c - 'A' + 10);
            } else {
                return 0;
            }
        }
    }

    private final static long[] BIT_AND_MASK_F_FOR_LONG = new long[]{
            0xFL,
            0xF0L,
            0xF00L,
            0xF000L,
            0xF0000L,
            0xF00000L,
            0xF000000L,
            0xF0000000L,
            0xF00000000L,
            0xF000000000L,
            0xF0000000000L,
            0xF00000000000L,
            0xF000000000000L,
            0xF0000000000000L,
            0xF00000000000000L,
            0xF000000000000000L
    };
    public static long hexToLong(String hex) {
        int len = hex.length();

        long val = 0;
        char c;
        int bitOffset = 0;
        int index = len - 1;
        for(int i = 0; i < len; i++) {
            c = hex.charAt(index);
            val |= ((hexCharToLong(c) << bitOffset) & BIT_AND_MASK_F_FOR_LONG[i]);

            index --;
            bitOffset +=4;
        }

        return val;
    }

    public static long hexCharToLong(char c) {
        if(c <= '9' && c >= '0') {
            return (long)(c - '0');
        } else {
            if(c >= 'a' && c <= 'f') {
                return (long)(c - 'a' + 10);
            } else if(c >= 'A' && c <= 'F') {
                return (long)(c - 'A' + 10);
            } else {
                return 0;
            }
        }
    }

}
