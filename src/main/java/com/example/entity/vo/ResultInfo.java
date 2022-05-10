package com.example.entity.vo;

import lombok.Data;

@Data
public class ResultInfo<T> {
    private String code;
    private String msg;
    private T data;

    public static <T> ResultInfo<T> success(T data) {
        ResultInfo<T> result = new ResultInfo<>();
        result.setCode("0");
        result.setMsg("成功");
        result.setData(data);
        return result;
    }

    public static <T> ResultInfo<T> success() {
        ResultInfo<T> result = new ResultInfo<>();
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    public static <T> ResultInfo<T> error(String code, String msg) {
        ResultInfo<T> result = new ResultInfo<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
