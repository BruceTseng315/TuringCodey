package turing.turingcodey.service;

import com.alibaba.fastjson.JSONObject;

public interface DeviceManagerService {
    JSONObject getDeviceInfo(String deviceSerialNumber);
    JSONObject sendDataToDevice(String deviceSerialNumber,String data) throws Exception;
}
