package com.happypeng.qx.secure;

import java.io.UnsupportedEncodingException;

/**
 * @author Hadoken
 * @date 2020/6/18
 */
public interface Encrypt {

    /**
     * rc4加密
     * @param content
     * @param key
     * @return
     */
    default String encrypt(String content, String key) {
        try {
            return Rc4Util.encryRC4String(content, key, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
