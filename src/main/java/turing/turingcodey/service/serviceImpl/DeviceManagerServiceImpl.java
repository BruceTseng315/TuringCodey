package turing.turingcodey.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import turing.turingcodey.core.utils.ArduinoUtils;
import turing.turingcodey.core.utils.DeviceManageUtils;
import turing.turingcodey.core.utils.LogUtil;
import turing.turingcodey.data.dao.AliDeviceMapper;
import turing.turingcodey.data.dao.custom.DeviceMapper;
import turing.turingcodey.data.model.AliDevice;
import turing.turingcodey.data.model.Device;
import turing.turingcodey.service.DeviceManagerService;

@Service
public class DeviceManagerServiceImpl implements DeviceManagerService{
    @Autowired
    DeviceMapper deviceMapper;
    @Autowired
    AliDeviceMapper aliDeviceMapper;
    @Override
    public JSONObject getDeviceInfo(String deviceSerialNumber) {
        LogUtil.print("deviceSerialNumber:"+deviceSerialNumber);
        JSONObject result = new JSONObject();
        Device device = deviceMapper.selectByDeviceSerialNumber(deviceSerialNumber);
        if(device == null){
            result.put("success",false);
            result.put("msg","无效设备");
            LogUtil.print("device is null");
            return result;
        }
        long aliDeviceId = device.getAliDeviceId();
        AliDevice aliDevice = aliDeviceMapper.selectByPrimaryKey(aliDeviceId);
        if(aliDevice == null){
            result.put("success",false);
            result.put("msg","无效设备");
            LogUtil.print("aliDevice id is :"+aliDeviceId);
            LogUtil.print("alidevice is null");
            return result;
        }
        String deviceName = aliDevice.getDeviceName();
        String productKey = aliDevice.getProductKey();
        JSONObject response = DeviceManageUtils.queryDeviceByName(productKey,deviceName);
        if(response.getBoolean("success") == false){
            result.put("success",false);
            result.put("msg","查询失败，请重试");
            return result;
        }else{
            String deviceInfo = response.getString("data");
            JSONObject deviceInfoJson = JSONObject.parseObject(deviceInfo);
            String status = deviceInfoJson.getString("deviceStatus");
            result.put("success",true);
            result.put("data",status);
            return  result;
        }

    }

    @Override
    public JSONObject sendDataToDevice(String deviceSerialNumber,String data) throws Exception{
        JSONObject result = new JSONObject();
        Device device = deviceMapper.selectByDeviceSerialNumber(deviceSerialNumber);
        if(device == null){
            result.put("success",false);
            result.put("msg","无效设备");
            return result;
        }
        long aliDeviceId = device.getAliDeviceId();
        AliDevice aliDevice = aliDeviceMapper.selectByPrimaryKey(aliDeviceId);
        if(aliDevice == null){
            result.put("success",false);
            result.put("msg","无效设备");
            return result;
        }
        String deviceName = aliDevice.getDeviceName();
        String productKey = aliDevice.getProductKey();
        String accessSecret = aliDevice.getDeviceSecret();
        JSONObject deviceInfo = this.getDeviceInfo(deviceSerialNumber);
        if(deviceInfo.getBoolean("success") == false) {
            result.put("success",false);
            result.put("msg",deviceInfo.getString("msg"));
            return result;
        }else{
            String status = deviceInfo.getString("data");
            if(!"ONLINE".equalsIgnoreCase(status)){
                result.put("success",false);
                result.put("msg",status);
                return result;
            }
            JSONObject compile = ArduinoUtils.compileUseArduinoBuilder(data);
            if(compile.getBoolean("success")==false){
                LogUtil.print("编译失败");
                result.put("success",false);
                result.put("msg","编译失败");
                return result;
            }
            JSONObject sendDataRes = DeviceManageUtils.sendDataToDevice(DeviceManageUtils.accessKey,DeviceManageUtils.accessSecret,productKey,deviceName,compile.getString("hex"));
            if(sendDataRes.getBoolean("success") == false){
                result.put("success",false);
                result.put("msg",sendDataRes.getString("msg"));
                return result;
            }else{
                result.put("success",true);
                result.put("data",sendDataRes.getString("data"));
                return result;
            }
        }

    }
}
