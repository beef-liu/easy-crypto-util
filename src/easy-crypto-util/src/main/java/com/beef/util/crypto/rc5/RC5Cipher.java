package com.beef.util.crypto.rc5;

import com.beef.util.BitUtil;
import com.beef.util.ByteArrayUtil;

import java.io.UnsupportedEncodingException;

/**
 * @author XingGu Liu
 */
public class RC5Cipher {
    private RC5KeyContext keyContext = null;
    private int[] sk = null;

    public RC5Cipher() {
        this.keyContext = new RC5KeyContext();
        init();
    }

    public RC5Cipher(byte[] key) {
        this.keyContext = new RC5KeyContext(key);
        init();
    }

    public RC5Cipher(byte[] key, int round) {
        this.keyContext = new RC5KeyContext(key, round);
        init();
    }

    public RC5Cipher(String keyStr) throws UnsupportedEncodingException {
        this.keyContext = new RC5KeyContext(keyStr.getBytes());
        init();
    }

    public RC5Cipher(String keyStr, String encoding) throws UnsupportedEncodingException {
        this.keyContext = new RC5KeyContext(keyStr.getBytes(encoding));
        init();
    }

    public RC5Cipher(String keyStr, int round) throws UnsupportedEncodingException {
        this.keyContext = new RC5KeyContext(keyStr.getBytes(), round);
        init();
    }

    public RC5Cipher(String keyStr, String encoding, int round) throws UnsupportedEncodingException {
        this.keyContext = new RC5KeyContext(keyStr.getBytes(encoding), round);
        init();
    }

    private void init() {
        sk = new int[keyContext.getKey().length - 2];
    }

    /**
     * @param originData:   Length of data must be even.
     * @param encryptedData
     */
    private void encrypt(int originData[], int encryptedData[]) {
        //int encryptedData[] = new int[originData.length];
        int blockCount = originData.length / 2;

//        int indexOfRC5ctx = 2;
        int indexOfd = 0;
        for (int h = 0; h < originData.length; h++) {
            encryptedData[h] = originData[h];
        }

//        int sk[] = new int[keyContext.getKey().length - 2];
        for (int h = 0; h < keyContext.getKey().length - 2; h++) {
            sk[h] = keyContext.getKey()[h + 2];
        }

        int rc = 0;
        int h = 0;
        int i = 0;

        for (h = 0; h < blockCount; h++) {
            encryptedData[indexOfd + 0] += keyContext.getKey()[0];
            encryptedData[indexOfd + 1] += keyContext.getKey()[1];
            for (i = 0; i < keyContext.getRound() * 2; i += 2) {
                encryptedData[indexOfd + 0] ^= encryptedData[indexOfd + 1];
                rc = encryptedData[indexOfd + 1] & 0x1f;
                encryptedData[indexOfd + 0] = BitUtil.ROTL32(encryptedData[indexOfd + 0], rc);
                encryptedData[indexOfd + 0] += sk[i];
                encryptedData[indexOfd + 1] ^= encryptedData[indexOfd + 0];
                rc = encryptedData[indexOfd + 0] & 0x1f;
                encryptedData[indexOfd + 1] = BitUtil.ROTL32(encryptedData[indexOfd + 1], rc);
                encryptedData[indexOfd + 1] += sk[i + 1];
            }

            indexOfd += 2;
        }

//        return encryptedData;
    }

    /**
     * @param encryptedData
     * @param originData
     */
    private void decrypt(int encryptedData[], int originData[]) {
        int blockCount = encryptedData.length / 2;
//        int originData[] = new int[encryptedData.length];
//        int indexOfRC5ctx = 2;
        int indexOfd = 0;
        for (int h = 0; h < encryptedData.length; h++) {
            originData[h] = encryptedData[h];
        }

//        int sk[] = new int[keyContext.getKey().length - 2];

        for (int h = 0; h < keyContext.getKey().length - 2; h++) {
            sk[h] = keyContext.getKey()[h + 2];
        }

        int h = 0;
        int i = 0;
        int rc = 0;

        for (h = 0; h < blockCount; h++) {
            for (i = keyContext.getRound() * 2 - 2; i >= 0; i -= 2) {
                originData[indexOfd + 1] -= sk[i + 1];
                rc = originData[indexOfd + 0] & 0x1f;
                originData[indexOfd + 1] = BitUtil.ROTR32(originData[indexOfd + 1], rc);
                originData[indexOfd + 1] ^= originData[indexOfd + 0];
                originData[indexOfd + 0] -= sk[i];
                rc = originData[indexOfd + 1] & 0x1f;
                originData[indexOfd + 0] = BitUtil.ROTR32(originData[indexOfd + 0], rc);
                originData[indexOfd + 0] ^= originData[indexOfd + 1];
            }

            originData[indexOfd + 0] -= keyContext.getKey()[0];
            originData[indexOfd + 1] -= keyContext.getKey()[1];
            indexOfd += 2;
        }

//        return originData;
    }

    /**
     * @param data: Length of data(int[]) must be times of 2.
     */
    public void encrypt(int data[]) {
        encrypt(data, data);
    }

    /**
     * @param data: Length of data(int[]) must be times of 2.
     */
    public void decrypt(int data[]) {
        decrypt(data, data);
    }

    /**
     * @param data:        Length of data(byte[]) must be times of 8.
     * @param tempIntBuff: Length of tempIntBuff(int[]) must be times of 2.
     */
    public void encrypt(byte data[], int[] tempIntBuff) {
        convertByteArrayToIntArray(data, tempIntBuff);
        encrypt(tempIntBuff, tempIntBuff);
        convertIntArrayToByteArray(tempIntBuff, data);
    }

    /**
     * @param data:        Length of data(byte[]) must be times of 8.
     * @param tempIntBuff: Length of tempIntBuff(int[]) must be times of 2.
     */
    public void decrypt(byte data[], int[] tempIntBuff) {
        convertByteArrayToIntArray(data, tempIntBuff);
        decrypt(tempIntBuff, tempIntBuff);
        convertIntArrayToByteArray(tempIntBuff, data);
    }

    /**
     * @param byteArray: Length of byteArray(byte[]) must be times of 8.
     * @param intArray:  Length of intArray(int[]) must be times of 2.
     */
    public static void convertByteArrayToIntArray(byte[] byteArray, int[] intArray) {
        int indexForIntArray = 0;

        for (int i = 0; i < byteArray.length; i += 4) {
            intArray[indexForIntArray] = ByteArrayUtil.getIntValueFromByteArrayLittleEndian(
                    byteArray, i);

            indexForIntArray++;
        }
    }

    public void updateKey(byte keyContent[]) {
        this.keyContext.updateKey(keyContent);
    }

    /**
     * @param intArray:  Length of intArray(int[]) must be times of 2.
     * @param byteArray: Length of byteArray(byte[]) must be times of 8.
     */
    public static void convertIntArrayToByteArray(int[] intArray, byte[] byteArray) {
        int indexForByteArray = 0;

        for (int i = 0; i < intArray.length; i++) {
            ByteArrayUtil.setValueToByteArrayLittleEndian(intArray[i], byteArray, indexForByteArray);

            indexForByteArray += 4;
        }
    }
}