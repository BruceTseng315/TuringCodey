package turing.turingcodey.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import turing.turingcodey.core.Result;
import turing.turingcodey.data.dao.custom.UserMapper;
import turing.turingcodey.data.model.User;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/turing/turingcodey")
public class TestController extends BaseController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/addUserIntoSession",method = RequestMethod.POST)
    public Result addUserIntoSession(HttpSession session, @RequestBody JSONObject params ){
        if(!params.containsKey("userId")){
            return resError(400,"缺少userId");
        }
        User user = userMapper.selectByPrimaryKey(params.getLong("userId"));
        if(user == null){
            return resError(400,"用户不存在！");
        }
        session.setAttribute("user",user);
        session.setMaxInactiveInterval(60*60*24);
        return resContent(user);

    }
}
