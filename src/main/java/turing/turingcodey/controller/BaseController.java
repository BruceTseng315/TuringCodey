package turing.turingcodey.controller;


import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.stereotype.Controller;
import turing.turingcodey.core.Result;
import turing.turingcodey.core.ResultEnum;
import turing.turingcodey.core.utils.ResultUtil;


/**
 * 虚拟Controller类，用于处理Controller里面 通用的方法，减少代码量
 * @author JasonC
 * @date 2017年6月13日14:28:30
 */
@Controller("baseController")
public abstract class BaseController {

  /**
   * 在父类中统一声明一个Bean复制类，用于给子类操作生成DTO todo:是否发生冲突？待验证
   */
  protected static final Mapper baseMapperUtil = new DozerBeanMapper();

  /**
   * 统一的返回格式，带参数输入的方法
   * @param code 返回码
   * @param data 返回数据，如没有则为空
   * @return 字符串化后的MsgInfo DTO JSON 体
   */
  protected Result resContent(int code, String message, Object data) {
    return ResultUtil.success(message,data);
  }
  protected Result resContent(String message, Object data) {
    return ResultUtil.success(message,data);
  }
  protected Result resContent(Object data) {
    return ResultUtil.success("success",data);
  }

  /**
   * 统一的返回格式，出错时的返回，只有一个参数输入,
   * @param code 返回码
   * @return 字符串化后的MsgInfo DTO, data数据为空
   */
  protected Result resError(int code) {
    return ResultUtil.error(code,"failure");
//    return ReturnFormat.resJson(code,null,null);
  }

  /**
   * 统一的返回格式，出错时的返回
   * @param code 返回码
   * @param message 返回的自定义描述
   * @return 字符串化后的MsgInfo DTO， data数据为空
   */
  protected Result resError(int code, String message) {
    return ResultUtil.error(code,message);
//    return ReturnFormat.resJson(code, message, null);
  }

  /**
   * 统一以错误码枚举的形式返回错误
   * @param errEnum 错误枚举类型
   * @return
   */
  protected Result resError(ResultEnum errEnum){
    return ResultUtil.error(errEnum.getCode(),errEnum.getMsg());
  }
  protected Result resError(ResultEnum errEnum,String msg){
    return ResultUtil.error(errEnum.getCode(),msg);
  }
}
