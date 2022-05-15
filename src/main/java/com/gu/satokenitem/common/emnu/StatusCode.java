package com.gu.satokenitem.common.emnu;


public enum StatusCode {
    //成功
    SUCCESS( 200, "SUCCESS" ),
    //失败
    FAILURE( 400, "error" );


    private int code;
    private String desc;

    StatusCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
