package turing.turingcodey.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import turing.turingcodey.data.dao.custom.DeviceMapper;
import turing.turingcodey.data.dao.custom.UserDeviceMapper;
import turing.turingcodey.data.model.UserDevice;
import turing.turingcodey.service.UserDeviceService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDeviceServiceImpl implements UserDeviceService {
    @Autowired
    UserDeviceMapper userDeviceMapper;
    @Autowired
    DeviceMapper deviceMapper;

    @Override
    public List<String> getMyDevices(long userId) {
        List<String> result = new ArrayList<>();
        List<UserDevice> userDeviceList = userDeviceMapper.selectByUserId(userId);
        if(userDeviceList != null && userDeviceList.size() > 0){
            for(UserDevice userDevice:userDeviceList){
                result.add(userDevice.getDeviceSerialNumber());
            }
        }
        return result;
    }

    @Override
    public JSONObject addUserDevice(long userId, String deviceSerialNumber) {
        JSONObject result = new JSONObject();

        if(deviceMapper.selectByDeviceSerialNumber(deviceSerialNumber) == null){
            result.put("success",false);
            result.put("msg","无效设备！");
            return result;
        }

        UserDevice userDevice = new UserDevice();
        userDevice.setUserId(userId);
        userDevice.setDeviceSerialNumber(deviceSerialNumber);

        UserDevice oldUserDevice = userDeviceMapper.selectByUserIdAndDeviceSerialNumber(userDevice);
        if(oldUserDevice != null){
            if(oldUserDevice.getIsDeleted() == 0){
                result.put("success",false);
                result.put("msg","设备已存在，勿重复添加");
                return result;
            }
            oldUserDevice.setIsDeleted((byte)0);
            userDeviceMapper.updateByPrimaryKeySelective(oldUserDevice);
        }else{
            userDeviceMapper.insertSelective(userDevice);
        }

        result.put("success",true);
        return result;
    }

    @Override
    public JSONObject deleteDevice(String deviceSerialNumber,long userId) {
        JSONObject result = new JSONObject();

        UserDevice userDevice = new UserDevice();
        userDevice.setUserId(userId);
        userDevice.setDeviceSerialNumber(deviceSerialNumber);
        UserDevice userDeviceRecord = userDeviceMapper.selectByUserIdAndDeviceSerialNumber(userDevice);
        if(userDeviceRecord == null) {
            result.put("success",false);
            result.put("msg","设备不存在");
            return result;
        }
        userDeviceMapper.deleteByUserIdAndDeviceSerialNumber(userDevice);
        result.put("success",true);
        result.put("msg","删除成功");

        return result;
    }
}
