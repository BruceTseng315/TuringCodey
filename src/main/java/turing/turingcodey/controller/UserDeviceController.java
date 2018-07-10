package turing.turingcodey.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import turing.turingcodey.core.Result;
import turing.turingcodey.core.ResultEnum;
import turing.turingcodey.data.model.User;
import turing.turingcodey.service.UserDeviceService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/turing/turingcodey")
public class UserDeviceController extends  BaseController {
    @Autowired
    UserDeviceService userDeviceService;

    @RequestMapping(value = "/devices/myDevices",method = RequestMethod.GET)
    public Result getMyDevices(HttpSession session ) {
        User user = (User) session.getAttribute("user");
        if (user == null){ return resError(ResultEnum.auth_illegal);}

        List<String> deviceSerialNumbers = userDeviceService.getMyDevices(user.getId());

        return resContent(deviceSerialNumbers);
    }

    @RequestMapping(value = "/devices/addDevice",method = RequestMethod.POST)
    public Result addUserDevice(HttpSession session,@RequestBody JSONObject params){
        User user = (User) session.getAttribute("user");
        if (user == null){ return resError(ResultEnum.auth_illegal);}
        if(!params.containsKey("deviceSerialNumber")){
            return resError(400,"请输入设备序列号");
        }

        JSONObject result = userDeviceService.addUserDevice(user.getId(),params.getString("deviceSerialNumber"));
        if(result.getBoolean("success")){
            return resContent(null);
        }else{
            return resError(400,result.getString("msg"));
        }
    }

    @RequestMapping(value = "/devices/deleteDevice",method = RequestMethod.POST)
    public Result deleteUserDevice(HttpSession session,@RequestBody JSONObject params){
        User user = (User) session.getAttribute("user");
        if (user == null){ return resError(ResultEnum.auth_illegal);}
        if(!params.containsKey("deviceSerialNumber")){
            return resError(400,"请输入设备序列号！");
        }

        JSONObject result = userDeviceService.deleteDevice(params.getString("deviceSerialNumber"),user.getId());
        if(result.getBoolean("success")){
            return resContent(null);
        }else{
            return resError(400,result.getString("msg"));
        }
    }
}
