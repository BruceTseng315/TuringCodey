package turing.turingcodey.service;

import com.alibaba.fastjson.JSONObject;
import turing.turingcodey.data.model.Work;

import java.util.List;

public interface WorkService {
    List<Work> getMyWorks(Work work);
    JSONObject saveWork(Work work);
    JSONObject downloadToDevice(String code, String deviceSerialNumber);
    JSONObject getWorkByWorkId(long workId);
    JSONObject deleteWorkByWorkId(long workId);
}
