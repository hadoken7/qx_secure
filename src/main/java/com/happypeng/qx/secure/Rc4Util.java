package com.happypeng.qx.secure;

/**
 * @author Hadoken
 */
public class Rc4Util {

    private static byte[] initKey(String key) {
        byte[] keyBytes = key.getBytes();
        byte[] state = new byte[256];

        for (int i = 0; i < 256; i++) {
            state[i] = (byte) i;
        }
        int index1 = 0;
        int index2 = 0;
        if (keyBytes.length == 0) {
            return null;
        }
        for (int i = 0; i < 256; i++) {
            index2 = ((keyBytes[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
            byte tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;
            index1 = (index1 + 1) % keyBytes.length;
        }
        return state;
    }

    public static byte[] rc4Base(byte[] input, String key) {
        int x = 0;
        int y = 0;
        byte[] keyBytes = initKey(key);
        int xorIndex;
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            x = (x + 1) & 0xff;
            y = ((keyBytes[x] & 0xff) + y) & 0xff;
            byte tmp = keyBytes[x];
            keyBytes[x] = keyBytes[y];
            keyBytes[y] = tmp;
            xorIndex = ((keyBytes[x] & 0xff) + (keyBytes[y] & 0xff)) & 0xff;
            result[i] = (byte) (input[i] ^ keyBytes[xorIndex]);
        }
        return result;
    }
}