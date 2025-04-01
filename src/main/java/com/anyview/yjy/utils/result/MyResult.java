package com.anyview.yjy.utils.result;

public class MyResult {
    private Integer code;
    private String msg;
    private Object data;

    public MyResult() {
    }

    public MyResult(Integer code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static String error(String msg) {
        String json = "{\"code\":\"500\",\"msg\":\""+msg+"\"}";
        return json;
    }

    public static String success() {
        String json = "{\"code\":\"200\",\"msg\":\""+ "success"+ "\",\"data\":null}";
        return json;
    }

    public static String success(Object data) {
        String json = "{\"code\":\"200\",\"msg\":\"success\",\"data\":" + data.toString() + "}";
        return json;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String toString() {
        return "MyResult{code = " + code + ", msg = " + msg + ", data = " + data + "}";
    }
}
