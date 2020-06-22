package com.happypeng.qx.secure;

import java.util.Base64;

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
        return Base64.getEncoder().encodeToString(Rc4Util.rc4(content,key).getBytes());
    }
}
