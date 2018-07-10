package turing.turingcodey.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import turing.turingcodey.core.Result;
import turing.turingcodey.core.ResultEnum;
import turing.turingcodey.data.model.User;
import turing.turingcodey.service.UserService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/turing/turingcodey")
public class UserController extends  BaseController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(HttpSession session, @RequestBody JSONObject params ) {

        if(!params.containsKey("userName")){
            return resError(400,"用户名为空！");
        }
        if(!params.containsKey("password") || params.getString("password").length() < 6){
            return resError(400,"密码至少为6位！");
        }

        String userName = params.getString("userName");
        String password = params.getString("password");
        JSONObject response = userService.login(userName,password);
        if(response.getBoolean("success")){
            User user = response.getObject("user",User.class);
            session.setAttribute("user",user);
            session.setMaxInactiveInterval(60*60*24);//设置过期时间为1天

            return resContent("success",user);
        }else{
            return resError(400,response.getString("msg"));
        }

    }
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result register(HttpSession session, @RequestBody JSONObject params) {
        if(!params.containsKey("userName")){
            return resError(400,"用户名不能为空！");
        }
        if(!params.containsKey("password") || params.getString("password").length() < 6){
            return resError(400,"密码至少为6位！");
        }
        String userName = params.getString("userName");
        String password = params.getString("password");
        JSONObject response = userService.register(userName,password);
        if(response.getBoolean("success")){
            User user = response.getObject("user",User.class);
            session.setAttribute("user",user);
            session.setMaxInactiveInterval(60*60*24);//设置过期时间为1天

            return resContent("success ",user);
        }else{
            return resError(400,response.getString("msg"));
        }
    }

    @RequestMapping(value = "/loginOut",method = RequestMethod.GET)
    public Result loginOut(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null){ return resError(ResultEnum.auth_illegal);}

        session.removeAttribute("user");
        return resContent(null);

    }

    @RequestMapping(value = "/checkLogin",method = RequestMethod.GET)
    public Result checkLogin(HttpSession session , @Param("userName")String userName) {
        User user = (User) session.getAttribute("user");
        if(user!=null && userName.equals(user.getUserName())){
            return resContent("已登录");
        }else{
            return resContent("未登录");
        }

    }
}
