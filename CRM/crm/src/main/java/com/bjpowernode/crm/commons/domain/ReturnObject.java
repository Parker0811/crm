package com.bjpowernode.crm.commons.domain;

/**
 * 姜宝
 * 2021/4/12
 */
public class ReturnObject {
    private String code;
    private String message;
    private Object returnData;

    public ReturnObject() {
    }

    public ReturnObject(String code, String message, Object returnData) {
        this.code = code;
        this.message = message;
        this.returnData = returnData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReturnData() {
        return returnData;
    }

    public void setReturnData(Object returnData) {
        this.returnData = returnData;
    }

    @Override
    public String toString() {
        return "ReturnObject{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", returnData=" + returnData +
                '}';
    }
}
