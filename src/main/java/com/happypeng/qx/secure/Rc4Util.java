package com.happypeng.qx.secure;

import java.util.Base64;

/**
 * @author Hadoken
 */
public class Rc4Util {

    public static String rc4(String context, String key) {
        int[] s = new int[256];
        byte[] k = new byte[256];
        for (int i = 0; i < 256; i++) {
            s[i] = i;
        }

        for (short i = 0; i < 256; i++) {
            k[i] = (byte) key.charAt((i % key.length()));
        }
        for (int i = 0, j = 0; i < 255; i++) {
            j = (j + s[i] + k[i]) % 256;
            int temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }
        char[] inputChar = context.toCharArray();
        char[] outputChar = new char[inputChar.length];
        for (int x = 0, i = 0, j = 0; x < inputChar.length; x++) {
            i = (i + 1) % 256;
            j = (j + s[i]) % 256;
            int temp = s[i];
            s[i] = s[j];
            s[j] = temp;
            int t = (s[i] + (s[j] % 256)) % 256;
            char cy = (char) s[t];
            outputChar[x] = (char) (inputChar[x] ^ cy);
        }
        return new String(outputChar);
    }


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

    public static void main(String[] args) {
        String inputStr = "Rc4Util";
        String key = "123";
        String str = Base64.getEncoder().encodeToString(rc4(inputStr,key).getBytes());
        System.out.println(str);
    }
}