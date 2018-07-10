package turing.turingcodey.data.dao.custom;

import turing.turingcodey.data.model.AliDevice;

public interface AliDeviceMapper extends turing.turingcodey.data.dao.AliDeviceMapper {
    long getMaxId();
    AliDevice selectByDeviceName(String deviceName);
}
