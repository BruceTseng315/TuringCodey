package turing.turingcodey.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import turing.turingcodey.core.Result;
import turing.turingcodey.core.ServiceResult;
import turing.turingcodey.data.dao.custom.UserMapper;
import turing.turingcodey.data.model.User;
import turing.turingcodey.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public JSONObject register(String userName, String password) {
        JSONObject result = new JSONObject();
        User user = userMapper.selectByUserName(userName);
        if(user != null){
            result.put("success",false);
            result.put("msg","用户名已存在！");
            return result;
        }else if(password == null || password.length() < 6){
            result.put("success",false);
            result.put("msg","密码长度至少为6位！");
            return result;
        }
        user = new User();
        user.setPassword(password);
        user.setUserName(userName);
        userMapper.insertSelective(user);

        result.put("success",true);
        result.put("user",user);

        return  result;
    }

    @Override
    public JSONObject login(String userName, String password) {
        JSONObject result = new JSONObject();
        System.out.println(">>>>>>>>>>>>>userName:"+userName);
        User user = userMapper.selectByUserName(userName);
        if(user == null){
            result.put("success",false);
            result.put("msg","该用户名不存在！");
            return result;
        }else if(!user.getPassword().equals(password)){
            result.put("success",false);
            result.put("msg","用户名或密码错误！");
            return result;
        }

        result.put("success",true);
        result.put("user",user);
        return  result;
    }
}
