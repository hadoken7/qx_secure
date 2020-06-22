package com.happypeng.qx.secure;

import java.util.Base64;

/**
 * @author Hadoken
 * @date 2020/6/18
 */
public interface Encrypt {

    /**
     * rc4加密
     *
     * @param content
     * @param key
     * @return
     */
    default String encrypt(String content, String key) {
        byte[] bytes = Rc4Util.rc4Base(content.getBytes(), key);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
