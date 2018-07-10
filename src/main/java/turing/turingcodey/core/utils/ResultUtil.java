package turing.turingcodey.core.utils;


import turing.turingcodey.core.Result;
import turing.turingcodey.core.ResultEnum;

/**
 *
 * @author JasonC
 * @date 2017-11-9-0009
 */
public class ResultUtil {
  public static Result success(String msg, Object object) {
    Result result = new Result();
    result.setSuccess(true);
    result.setCode(0);
    result.setMsg(msg);
    result.setData(object);
    return result;
  }
  public static Result success(Object object) {
    Result result = new Result();
    result.setCode(0);
    result.setSuccess(true);
    result.setMsg("success");
    result.setData(object);
    return result;
  }
  public static Result success() {
    return success(null);
  }

  public static Result error(Integer code, String msg) {
    Result result = new Result();
    result.setSuccess(false);
    result.setCode(code);
    result.setMsg(msg);
    return result;
  }
  public static Result error(ResultEnum resultEnum){
    return ResultUtil.error(resultEnum.getCode(),resultEnum.getMsg());
  }
}
