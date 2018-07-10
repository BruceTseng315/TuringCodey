package turing.turingcodey.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import turing.turingcodey.core.Result;
import turing.turingcodey.core.ResultEnum;
import turing.turingcodey.data.model.User;
import turing.turingcodey.data.model.Work;
import turing.turingcodey.service.WorkService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/turing/turingcodey")
public class WorkController  extends  BaseController {
    @Autowired
    WorkService workService;
    @Autowired
    private Mapper baseMapperUtil;

    @RequestMapping(value = "/works/myWorks",method = RequestMethod.GET)
    public Result getMyWorks(HttpSession session,@Param("workType")Byte workType){
        User user = (User) session.getAttribute("user");
        if (user == null){ return resError(ResultEnum.auth_illegal);}
        Work work = new Work();
        work.setUserId(user.getId());
        if(workType != null){
            work.setWorkType(workType);
        }else{
            work.setWorkType((byte)0);
        }
        List<Work> workList = workService.getMyWorks(work);

        return resContent(workList);
    }

    @RequestMapping(value = "/works/save",method = RequestMethod.POST)
    public Result saveWork(HttpSession session, @RequestBody JSONObject params){
        System.out.println("params:"+params);
        User user = (User) session.getAttribute("user");
        if (user == null){ return resError(ResultEnum.auth_illegal);}

        if(!params.containsKey("workName")){
            return resError(400,"参数中缺少作品名称");
        }
        Work work = new Work();

        baseMapperUtil.map(params,work);
        if(params.getByte("workType") == null){
            work.setWorkType((byte)0);
        }else{
            work.setWorkType(params.getByte("workType"));
        }
        System.out.println("type:"+work.getWorkType());
        work.setUserId(user.getId());
        JSONObject result = workService.saveWork(work);
        if(result.getBoolean("success")){
            return resContent(result.getObject("data",Work.class));
        }else{
            return resError(400,result.getString("msg"));
        }

    }

    @RequestMapping(value = "/works/workDetail",method = RequestMethod.GET)
    public Result getWorkByWorkId(HttpSession session,@Param("workId")Long workId){
        User user = (User) session.getAttribute("user");
        if (user == null){ return resError(ResultEnum.auth_illegal);}
        JSONObject result = workService.getWorkByWorkId(workId);
        if(result.getBoolean("success") == false){
            return resError(400,result.getString("msg"));
        }else{
            return resContent(result.getObject("data",Work.class));
        }

    }

    @RequestMapping(value = "/works/delete",method = RequestMethod.POST)
    public Result deleteWorkByWorkId(HttpSession session, @RequestBody JSONObject params){
        User user = (User) session.getAttribute("user");
        if (user == null){ return resError(ResultEnum.auth_illegal);}

        if(!params.containsKey("workId")){
            return resError(400,"参数中缺少作品id");
        }
        long workId = params.getLong("workId");
        JSONObject result = workService.deleteWorkByWorkId(workId);
        if(result.getBoolean("success")){
            return resContent(null);
        }else{
            return resError(400,result.getString("msg"));
        }

    }
}
