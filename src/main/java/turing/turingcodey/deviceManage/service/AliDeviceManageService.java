package turing.turingcodey.deviceManage.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface AliDeviceManageService {
    //批量申请设备
    JSONObject applyDeviceWithNames(String productKey,List<String> deviceNames) throws Exception;
    //注册设备
    JSONObject registerDevice(String deviceName);
    JSONObject queryApplyStatus(long applyId)throws Exception;
    JSONObject queryPageByApplyId(long applyId,int pageSize,int currentPage) throws Exception;
}
