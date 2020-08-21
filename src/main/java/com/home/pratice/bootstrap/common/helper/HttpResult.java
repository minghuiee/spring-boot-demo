package com.home.pratice.bootstrap.common.helper;

import com.google.gson.Gson;

public class HttpResult {
    private Integer status;
    private String message;
    private Object result;
    private static Gson gson = new Gson();

    private HttpResult(Integer status, String message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public static String result(Integer status, String message, Object result) {
        return gson.toJson(new HttpResult(status, message, result));
    }

    public static String result(Integer status, String message) {
        return gson.toJson(new HttpResult(status, message, "no body"));
    }
}
