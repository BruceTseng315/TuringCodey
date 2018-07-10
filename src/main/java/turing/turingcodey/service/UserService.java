package turing.turingcodey.service;


import com.alibaba.fastjson.JSONObject;
import turing.turingcodey.core.ServiceResult;

public interface UserService {
    JSONObject register(String userName, String password);
    JSONObject login(String userName, String password);
}
