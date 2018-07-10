package turing.turingcodey.data.dao.custom;

import turing.turingcodey.data.model.Work;

import java.util.List;
import java.util.Map;

public interface WorkMapper extends turing.turingcodey.data.dao.WorkMapper {
    List<Work> selectByUserId(Work work);
    Work selectByWorkName(Work work);
}
