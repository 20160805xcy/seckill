package com.xcy.dto;

//所有ajax返回的数据--封装json结果
public class SeckillResult<T> {
    //请求是否成功的结果 不是 秒杀是否成功的结果
    private boolean success;

    private T data;

    private String error;

    //true
    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    //false
    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
