package com.beef.util;

/**
 * 
 * @author XingGu Liu
 *
 */
public class BitUtil {
    public static int ROTL32(int x, int c)
    {
        int mask = (int)((1L << c) - 1L);
        int a = (int)(long)x << c & -1 - mask;
        int b = (int)(long)x >> 32 - c & mask;
        int e = a | b;
        return e;
    }

    public static int ROTR32(int x, int c)
    {
        int mask = (int)((1L << 32 - c) - 1L);
        int a = (int)(long)x << 32 - c & -1 - mask;
        int b = (int)(long)x >> c & mask;
        int e = a | b;
        return e;
    }
}
