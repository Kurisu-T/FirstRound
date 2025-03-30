package com.anyview.yjy.utils.result;

import com.anyview.yjy.utils.jsonUtils;

public class Result<T> {
    private String code;
    private String msg;
    private Object data;

//    public String toJson(Result res) {
//        ObjectMapper mapper = new ObjectMapper();
//
//        try {
//            return mapper.writeValueAsString(res);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return "{\"code\":\"" + 500 + "\",\"msg\":\"" + "json 转化失败" + "\"}";
//    }

    public static String success() {
        Result result = new Result();
        result.setCode("200");
        result.setMsg("success");
        return jsonUtils.toJson(result);
    }

    public static String success(Object data) {
        Result result = new Result();
        result.setCode("200");
        result.setMsg("success");
        result.setData(data);
        return jsonUtils.toJson(result);
    }

    public static String error(String msg) {
        Result result = new Result();
        result.setCode("500");
        result.setMsg(msg);
        return jsonUtils.toJson(result);
    }

    public Result() {
    }

    public Result(String code) {
        this.code = code;
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public void setData(Object data) {
        this.data = data;
    }

    public String toString() {
        return "Result{code = " + code + ", msg = " + msg + ", data = " + data + "}";
    }
}
