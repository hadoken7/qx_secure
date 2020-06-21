package com.happypeng.qx.secure;

import lombok.Data;

import java.util.List;

/**
 * @author Hadoken
 * @date 2020/6/18
 */

@Data
public class QxConfig {
    private List<String> plains;
    private List<String> urls;
    private String key;
}
