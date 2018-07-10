package turing.turingcodey.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import turing.turingcodey.data.dao.AliDeviceMapper;
import turing.turingcodey.data.dao.custom.DeviceMapper;
import turing.turingcodey.data.dao.custom.WorkMapper;
import turing.turingcodey.data.model.AliDevice;
import turing.turingcodey.data.model.Device;
import turing.turingcodey.data.model.Work;
import turing.turingcodey.service.WorkService;

import java.util.List;
import java.util.Map;

@Service
public class WorkServiceImpl implements WorkService {
    @Autowired
    WorkMapper workMapper;
    @Autowired
    DeviceMapper deviceMapper;
    @Autowired
    AliDeviceMapper aliDeviceMapper;

    @Override
    public List<Work> getMyWorks(Work work) {
        List<Work> workList = workMapper.selectByUserId(work);
        return workList;
    }

    @Override
    public JSONObject saveWork(Work work) {
        JSONObject result = new JSONObject();
        //如果参数中有workId，就更新
        if(work.getId() != null){
            if(workMapper.selectByPrimaryKey(work.getId()) != null){
                workMapper.updateByPrimaryKey(work);
                result.put("success",true);
                return result;
            }else{
                result.put("success",false);
                result.put("msg","作品不存在");
                return result;
            }
        }
        //如果作品参数中没有workId，就新建
        if(work.getId() == null){
            //判断作品名称是否重复

            if(workMapper.selectByWorkName(work) != null) {
                result.put("success",false);
                result.put("msg","作品名称重复");
                return result;
            }
            System.out.println("graph:"+work.getGraph());
            if(work.getCode()!=null)
            System.out.println("code.len:"+work.getCode().length());
            if(work.getGraph()!=null)
            System.out.println("graph.len:"+work.getGraph().length());
            workMapper.insertSelective(work);
            work = workMapper.selectByPrimaryKey(work.getId());
            result.put("success",true);
            result.put("data",work);
        }
        return result;
    }

    @Override
    public JSONObject downloadToDevice(String code, String deviceSerialNumber) {
        JSONObject result = new JSONObject();
        Device device = deviceMapper.selectByDeviceSerialNumber(deviceSerialNumber);
        if(device == null) {
            result.put("success",false);
            result.put("msg","无效设备！");
            return result;
        }
        long aliDeviceId = device.getAliDeviceId();
        AliDevice aliDevice = aliDeviceMapper.selectByPrimaryKey(aliDeviceId);
        if(aliDevice == null) {
            result.put("success",false);
            result.put("msg","无效设备！");
            return result;
        }
        String aliDeviceName = aliDevice.getDeviceName();
        return null;
    }

    @Override
    public JSONObject getWorkByWorkId(long workId) {
        JSONObject result = new JSONObject();

        Work work = workMapper.selectByPrimaryKey(workId);
        if(work == null){
            result.put("success",false);
            result.put("msg","作品不存在");
            return result;
        }

        result.put("success",true);
        result.put("data",work);
        return result;
    }

    @Override
    public JSONObject deleteWorkByWorkId(long workId) {
        JSONObject result = new JSONObject();

//        Work work = workMapper.selectByPrimaryKey(workId);
//        if(work == null){
//            result.put("success",false);
//            result.put("msg","作品不存在");
//            return result;
//        }
//        work.setIsDeleted((byte)1);
//        workMapper.updateByPrimaryKeySelective(work);
        workMapper.deleteByPrimaryKey(workId);

        result.put("success",true);
        return result;
    }
}
