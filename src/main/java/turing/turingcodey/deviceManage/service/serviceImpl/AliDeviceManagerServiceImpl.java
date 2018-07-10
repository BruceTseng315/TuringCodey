package turing.turingcodey.deviceManage.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.iot.model.v20170420.*;
import org.springframework.stereotype.Service;
import turing.turingcodey.core.utils.DeviceManageUtils;
import turing.turingcodey.core.utils.LogUtil;
import turing.turingcodey.deviceManage.service.AliDeviceManageService;

import java.util.List;

@Service
public class AliDeviceManagerServiceImpl implements AliDeviceManageService {
    private DefaultAcsClient client = DeviceManageUtils.iotClient;

    @Override
    public JSONObject applyDeviceWithNames(String productKey,List<String> deviceNames) throws Exception{
        JSONObject result = new JSONObject();

        ApplyDeviceWithNamesRequest request = new ApplyDeviceWithNamesRequest();
        request.setDeviceNames(deviceNames);
        request.setProductKey(productKey);
        ApplyDeviceWithNamesResponse response = client.getAcsResponse(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print(
                    "申请设备成功！applyID:" + response.getApplyId() + ",requestId:" + response.getRequestId());
            result.put("success",true);
            result.put("applyId",response.getApplyId());
            return result;
        } else {
            LogUtil.error("申请设备失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());
            result.put("success",false);
            result.put("mag",response.getErrorMessage());
            return result;
        }
    }

    @Override
    public JSONObject registerDevice(String deviceName) {
        return null;
    }

    @Override
    public JSONObject queryApplyStatus(long applyId) throws  Exception{
        JSONObject result = new JSONObject();
        QueryApplyStatusRequest request = new QueryApplyStatusRequest();
        request.setApplyId(applyId);
        QueryApplyStatusResponse response = client.getAcsResponse(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print(
                    "查询批量设备申请状态成功！Finish:" + response.getFinish() + ",errMsg:" + response.getErrorMessage());
            result.put("success",true);
            result.put("finish",response.getFinish());
            result.put("msg",response.getErrorMessage());
            return result;
        } else {
            LogUtil.error("查询批量设备申请状态失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());
            result.put("success",false);
            result.put("mag",response.getErrorMessage());
            return result;
        }
    }

    @Override
    public JSONObject queryPageByApplyId(long applyId,int pageSize, int currentPage) throws Exception {
        JSONObject result = new JSONObject();
        QueryPageByApplyIdRequest request = new QueryPageByApplyIdRequest();
        request.setApplyId(applyId);
        request.setCurrentPage(currentPage);
        request.setPageSize(pageSize);
        QueryPageByApplyIdResponse response = client.getAcsResponse(request);
        if (response != null && response.getSuccess() != false) {
            LogUtil.print("创查询设备信息成功！");
            result.put("success",true);
            result.put("data",response);
            return result;
        } else {
            LogUtil.error("查询设备信息失败！requestId:" + response.getRequestId() + "原因：" + response.getErrorMessage());
            result.put("success",false);
            result.put("msg",response.getErrorMessage());
            return result;
        }
    }
}
