package turing.turingcodey.core;

import java.io.Serializable;

/**
 * http请求返回的最外层对象
 * @author JasonC
 * @date 2017-11-9-0009
 */
public class Result<T> implements Serializable {

  /** 错误码. */
  private Integer code;

  /** 提示信息. */
  private String msg;

  /** 具体的内容. */
  private T data;

  private boolean success;

//  public Result(int code, boolean success, String msg, T data){
//    this.code = code;
//    this.success =  success;
//    this.msg = msg;
//    this.data = data;
//  }

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

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }
}
