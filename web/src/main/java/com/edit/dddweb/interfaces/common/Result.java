package com.edit.dddweb.interfaces.common;

import lombok.Data;

import java.util.List;

@Data
public class Result<T> {
    private int code = 200;
    private Long total;
    private String msg;
    private T data;

    public static Result<String> SUCCESS = new Result<>(200, "请求成功");

    public static Result<String> ERROR = new Result<>(500, "请求失败");

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(int code, String msg, Long total,  T data) {
        this.code = code;
        this.msg = msg;
        this.total = total;
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, null, data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg);
    }

    public static <T> Result<List<T>> page(long total, List<T> list) {
        return  new Result<>(200, null, total, list);
    }
}
