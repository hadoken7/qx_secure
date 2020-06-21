package com.happypeng.qx.secure;

import lombok.Data;

/**
 * @author Hadoken
 * @date 2020/6/18
 */
@Data
public class Result {
    private int code;
    private String msg;
    private String data;

    public Result(String data){
        this.data = data;
        this.code = 0;
        this.msg = "success";
    }

    public Result(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
