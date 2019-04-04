package com.wjj.cloud.bfxy.common.msg;

/**
 * @Author: wangjunjie 2018/10/12 14:36
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2018/10/12 14:36
 */
public class ApiResult<T> {

    private boolean ret;

    private int status = 200;

    private T data;

    private String msg;

    private int total;

    public boolean isRet() {
        return ret;
    }

    public void setRet(boolean ret) {
        this.ret = ret;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static<T> ApiResult onSuccess(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setRet(true);
        result.setData(data);
        result.setStatus(200);
        return result;
    }

    public static<T> ApiResult onSuccess() {
        ApiResult<T> result = new ApiResult<>();
        result.setRet(true);
        result.setData(null);
        result.setStatus(200);
        return result;
    }

    public static<T> ApiResult onSuccess(String msg,T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setRet(true);
        result.setData(data);
        result.setMsg(msg);
        result.setStatus(200);
        return result;
    }

    public static<T> ApiResult onSuccess(T data,int total) {
        ApiResult<T> result = new ApiResult<>();
        result.setRet(true);
        result.setData(data);
        result.setTotal(total);
        return result;
    }

    public static<T> ApiResult onError(String esgMsg) {
        ApiResult<T> result = new ApiResult<>();
        result.setRet(false);
        result.setData(null);
        result.setMsg(esgMsg);
        result.setStatus(1000);
        return result;
    }

    public static<T> ApiResult onError() {
        ApiResult<T> result = new ApiResult<>();
        result.setRet(false);
        result.setData(null);
        result.setMsg("");
        result.setStatus(1000);
        return result;
    }

    public static<T> ApiResult onError(String esgMsg,int status) {
        ApiResult<T> result = new ApiResult<>();
        result.setRet(false);
        result.setData(null);
        result.setMsg(esgMsg);
        result.setStatus(status);
        return result;
    }


}
