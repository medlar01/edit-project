package com.edit.web.vo;

public class JsonVO<T> {
    private Integer total;
    private T data;
    private Integer code;
    private String msg;

    public static JsonVO<String> fail(String msg) {
        JsonVO<String> ret = new JsonVO<>();
        ret.setCode(500);
        ret.setMsg(msg);
        return ret;
    }

    public static JsonVO<String> success() {
        JsonVO<String> ret = new JsonVO<>();
        ret.setCode(200);
        return ret;
    }

    public static <T> JsonVO<T> success(T data) {
        JsonVO<T> ret = new JsonVO<>();
        ret.setCode(200);
        ret.setData(data);
        return ret;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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
}
