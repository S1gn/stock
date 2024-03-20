package com.s1gn.stock.vo.resp;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @ClassName R
 * @Description 统一返回相应消息结果
 * @Author S1gn
 * @Date 18:44
 * @Version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> implements Serializable {
    private static final int SUCCESS_CODE = 1;
    private static final int ERROR_CODE = 0;
    private int code;
    private String msg;
    private T data;
    private R(int code) {
        this.code = code;
    }
    private R(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private R(int code, T data) {
        this.code = code;
        this.data = data;
    }
    private R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> R<T> ok() {
        return new R<T>(SUCCESS_CODE, "success");
    }
    public static <T> R<T> ok(String msg) {
        return new R<T>(ERROR_CODE, msg);
    }
    public static <T> R<T> ok(T data) {
        return new R<T>(SUCCESS_CODE,  data);
    }
    public static <T> R<T> ok(String msg, T data) {
        return new R<T>(SUCCESS_CODE, msg, data);
    }
    public static <T> R<T> error() {
        return new R<T>(ERROR_CODE, "error");
    }
    public static <T> R<T> error(String msg) {
        return new R<T>(ERROR_CODE, msg);
    }
    public static <T> R<T> error(int code, String msg) {
        return new R<T>(code, msg);
    }
    public static <T> R<T> error(ResponseCode res) {
        return new R<T>(res.getCode(), res.getMsg());
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
