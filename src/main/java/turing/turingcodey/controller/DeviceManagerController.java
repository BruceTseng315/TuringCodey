package turing.turingcodey.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import turing.turingcodey.core.Result;
import turing.turingcodey.core.ResultEnum;
import turing.turingcodey.core.utils.CompileUtils;
import turing.turingcodey.core.utils.LogUtil;
import turing.turingcodey.data.model.User;
import turing.turingcodey.service.DeviceManagerService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/turing/turingcodey")
public class DeviceManagerController extends BaseController {
    @Autowired
    DeviceManagerService deviceManagerService;

    /**
     * 查询设备状态
     * @param session
     * @param deviceSerialNumber
     * @return "ONLINE"  "OFFLINE" "UNACTIVE"
     */
    @RequestMapping(value = "/devices/getStatus",method = RequestMethod.GET)
    public Result getDeviceStatus(HttpSession session,@Param("deviceSerialNumber")String deviceSerialNumber) {
        User user = (User) session.getAttribute("user");
        if (user == null){ return resError(ResultEnum.auth_illegal);}

        JSONObject result = deviceManagerService.getDeviceInfo(deviceSerialNumber);
        if(result.getBoolean("success") == false){
            return resError(400,result.getString("msg"));
        }else{
            return resContent(result.getString("data"));
        }
    }

    @RequestMapping(value = "/devices/download",method = RequestMethod.POST)
    public Result sendDataToDevice(HttpSession session, @RequestBody JSONObject params){
        User user = (User) session.getAttribute("user");
        if (user == null){ return resError(ResultEnum.auth_illegal);}

        if(!(params.containsKey("deviceSerialNumber")&&params.containsKey("code"))){
            return resError(400,"设备序列号或代码不能为空");
        }
        String deviceSerialNumber = params.getString("deviceSerialNumber");
        String code = params.getString("code");
        System.out.println("code :"+code);
        //查询设备状态
        JSONObject deviceInfo = deviceManagerService.getDeviceInfo(deviceSerialNumber);
        if(deviceInfo.getBoolean("success") == false) {
            return resError(500,"设备状态未知，请重试");
        }
        String status = deviceInfo.getString("data");
        if(!"ONLINE".equalsIgnoreCase(status)){
            return resError(500,status);
        }


        JSONObject result = null;
        try {
             result = deviceManagerService.sendDataToDevice(deviceSerialNumber, code);
            if(result.getBoolean("success") == false){
                return resError(400,result.getString("msg"));
            }else{
                return resContent(result.getString("data"));
            }
        }catch (Exception e){
            LogUtil.print(e.getMessage());
            return resError(400,"数据发送出现未知异常");
        }
    }

}
