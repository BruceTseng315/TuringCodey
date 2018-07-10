package turing.turingcodey.deviceManage.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.iot.model.v20170420.QueryPageByApplyIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import turing.turingcodey.controller.BaseController;
import turing.turingcodey.core.Result;
import turing.turingcodey.core.utils.LogUtil;
import turing.turingcodey.data.dao.custom.AliDeviceMapper;
import turing.turingcodey.data.model.AliDevice;
import turing.turingcodey.deviceManage.service.AliDeviceManageService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/turing/turingcodey/aliDeviceManage")
public class AliDeviceManageController extends BaseController {
    private final String deviceNamePrefix = "turingcodey";
    public static  String accessKey;
    public static  String accessSecret;
    public static String productKey;
    static{
        Properties prop = new Properties();
        try {
            prop.load(Object.class.getResourceAsStream("/config.properties"));
            accessKey = prop.getProperty("user.accessKeyID");
            accessSecret = prop.getProperty("user.accessKeySecret");
            productKey = prop.getProperty("iot.productKey");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Autowired
    AliDeviceMapper aliDeviceMapper;
    @Autowired
    AliDeviceManageService deviceManageService;

    /**
     * 批量申请设备
     * @param session
     * @param params
     * @return
     */
    @RequestMapping(value = "/registerDevices",method = RequestMethod.POST)
    public Result registerDevices(HttpSession session, @RequestBody JSONObject params) {
        if(!params.containsKey("num")) {
            return resError(400,"缺少参数num");
        }
        int num = params.getInteger("num");
        if(num > 1000){
            return resError(400,"单次申请的设备数量不能超过1000");
        }
        //设备名为设备前缀+自增id
        long beginIndex = aliDeviceMapper.getMaxId();
        List<String> deviceNames = new ArrayList<>(num);
        for(int i = 0;i < num;i++){
            deviceNames.add(deviceNamePrefix + "_" + (beginIndex + i));
        }
        try {
            //发起申请
            JSONObject applyResult = deviceManageService.applyDeviceWithNames(productKey, deviceNames);
            if(applyResult.getBoolean("success") == false){
                return resError(500,applyResult.getString("msg"));
            }else{
                long applyId = applyResult.getLong("applyId");
                //查询申请状态并保存
                boolean save = saveAliDeviceByApplyId(applyId);
                if(save){
                    return resContent("设备申请并保存成功");
                }else{
                    return resContent("设备申请成功但保存失败，请稍后查询");
                }
            }
        }catch(Exception e){
            LogUtil.print("批量申请设备出错,原因："+e.getMessage());
            return resError(500,"批量申请设备出错,原因："+e.getMessage());
        }



    }
    private boolean saveAliDeviceByApplyId(long applyId){
        int tryTimes = 3;
        int count = 0;
        try {
            while (count < tryTimes) {
                count++;
                //查询申请状态
                JSONObject applyStatus = deviceManageService.queryApplyStatus(applyId);
                if (applyStatus.getBoolean("success") == false) {
                    LogUtil.print("查询申请状态失败，请稍后重试");
                    return false;
                } else {
                    if (applyStatus.getBoolean("finish") == false) {
                        LogUtil.print("申请中......");
                        Thread.sleep(1000);
                    }else{
                        LogUtil.print("申请完成");
                        JSONObject queryDeviceInfo = deviceManageService.queryPageByApplyId(applyId,1000,1);
                        if(queryDeviceInfo.getBoolean("success") == false) {
                            LogUtil.print("查询设备信息失败,原因："+queryDeviceInfo.getString("msg"));
                            Thread.sleep(1000);
                        }else {
                            QueryPageByApplyIdResponse response = queryDeviceInfo.getObject("data",QueryPageByApplyIdResponse.class);
                            List<QueryPageByApplyIdResponse.ApplyDeviceInfo> applyDeviceList = response.getApplyDeviceList();
                            for(QueryPageByApplyIdResponse.ApplyDeviceInfo deviceInfo:applyDeviceList){
                                AliDevice aliDevice = new AliDevice();
                                aliDevice.setDeviceName(deviceInfo.getDeviceName());
                                aliDevice.setProductKey(productKey);
                                aliDevice.setDeviceSecret(deviceInfo.getDeviceSecret());
                                aliDevice.setDeviceId(deviceInfo.getDeviceId());
                                AliDevice record = aliDeviceMapper.selectByDeviceName(deviceInfo.getDeviceName());
                                if(record != null){
                                    LogUtil.print("设备："+deviceInfo.getDeviceName()+" 已存在");
                                    continue;
                                }else{
                                    aliDeviceMapper.insertSelective(aliDevice);
                                    LogUtil.print("设备："+deviceInfo.getDeviceName()+" 保存成功");
                                }
                            }
                            return true;
                        }

                    }
                }
            }
        }catch(Exception e){
            LogUtil.print("保存设备信息失败，错误信息："+e.getMessage());

        }

        LogUtil.print("设备保存失败");
        return false;
    }
}
