package turing.turingcodey.data.dao.custom;

import turing.turingcodey.data.model.UserDevice;

import java.util.List;

public interface UserDeviceMapper extends turing.turingcodey.data.dao.UserDeviceMapper {
    List<UserDevice> selectByUserId(long userId);
    UserDevice selectByUserIdAndDeviceSerialNumber(UserDevice userDevice);
    int deleteByUserIdAndDeviceSerialNumber(UserDevice userDevice);
}
