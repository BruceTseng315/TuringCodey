package turing.turingcodey.core;

/**
 * 响应码错误枚举
 * @author JasonC
 * @date 2018-1-6-0006
 */
public enum ResultEnum {

  params_illegal(40000,"请求参数非法"),
  auth_illegal(40002,"没有访问权限"),

  order_notFound(40401,"订单已删除或不存在"),

  deliver_corp_notSupport(40501,"不支持的快递公司"),
  deliver_subscribe_fail(40502,"快递鸟订阅失败"),
  deliver_infoUpdate_fail(40503,"不支持的物流更新操作"),

  refund_operate_illegal(40601,"退款操作非法"),
  refund_operate_notSupport(40602,"退款操作不支持"),
  refund_moneyInput_fail(40603,"退款金额错误,不要大于总订单金额数"),
  ;
  private Integer code;
  private String msg;
  ResultEnum(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public Integer getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }
}
