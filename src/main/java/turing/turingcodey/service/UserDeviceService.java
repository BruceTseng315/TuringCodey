package turing.turingcodey.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface UserDeviceService {
    List<String> getMyDevices(long userId);
    JSONObject addUserDevice(long userId, String deviceSerialNumber);
    JSONObject deleteDevice(String deviceSerialNumber,long userId);
}
