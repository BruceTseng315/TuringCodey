package turing.turingcodey.data.dao.custom;

import turing.turingcodey.data.model.Device;

public interface DeviceMapper extends turing.turingcodey.data.dao.DeviceMapper {
    Device selectByDeviceSerialNumber(String deviceSerialNumber);
}
