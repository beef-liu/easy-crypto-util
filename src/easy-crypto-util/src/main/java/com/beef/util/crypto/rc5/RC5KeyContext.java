package com.beef.util.crypto.rc5;

import com.beef.util.BitUtil;


/**
 * @author XingGu Liu
 */
public class RC5KeyContext {

    private int key[];

    private int round;

    public int[] getKey() {
        return key;
    }

    public int getRound() {
        return round;
    }

    public RC5KeyContext() {
        this.round = 16;
        this.key = new int[round * 2 + 2];
    }

    /**
     * @param round: default 16
     */
    public RC5KeyContext(int round) {
        this.round = round;
        this.key = new int[round * 2 + 2];
    }

    public RC5KeyContext(byte keyContent[]) {
        this.round = 16;
        this.key = new int[round * 2 + 2];
        updateKey(keyContent);
    }

    /**
     * @param round: default 16
     */
    public RC5KeyContext(byte keyContent[], int round) {
        this.round = round;
        this.key = new int[round * 2 + 2];
        updateKey(keyContent);
    }

    public void updateKey(byte keyContent[]) {
        int keylen = keyContent.length;
        int xk_len = this.round * 2 + 2;
        int pk_len = keylen / 4;

        if (keylen % 4 != 0) {
            pk_len++;
        }

        int pk[] = new int[pk_len];
        byte cp[] = new byte[pk_len * 4];

        for (int i = 0; i < cp.length; i++)
            if (i < keylen) {
                cp[i] = keyContent[i];
            } else {
                cp[i] = 0;
            }

        int tmp = 0;
        for (int i = 0; i < pk_len; i++) {
            tmp = 0;
            tmp = cp[i * 4] << 24 & 0xff000000;
            //Modify
            /*
            tmp += cp[i * 4 + 1] << 16 & 0xffff0000;
            tmp += cp[i * 4 + 2] << 8 & 0xffffff00;
            tmp += cp[i * 4 + 3];
            */
            tmp |= cp[i * 4 + 1] << 16 & 0x00ff0000;
            tmp |= cp[i * 4 + 2] << 8 & 0x0000ff00;
            tmp |= cp[i * 4 + 3] & 0x000000ff;
            pk[i] = tmp;
        }

        this.key[0] = 0xb7e15163;
        for (int i = 1; i < xk_len; i++) {
            this.key[i] = this.key[i - 1] + 0x9e3779b9;
        }

        int num_steps;
        if (pk_len > xk_len) {
            num_steps = 3 * pk_len;
        } else {
            num_steps = 3 * xk_len;
        }

        int A = 0;
        int B = 0;
        for (int i = 0; i < num_steps; i++) {
//            int tmp2 = ROTL32(c.xk[i % xk_len] + A + B, 3);
            A = this.key[i % xk_len] = BitUtil.ROTL32(this.key[i % xk_len] + A + B, 3);
            int rc = A + B & 0x1f;
            B = pk[i % pk_len] = BitUtil.ROTL32(pk[i % pk_len] + A + B, rc);
        }
    }
}