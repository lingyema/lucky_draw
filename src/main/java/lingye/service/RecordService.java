package lingye.service;

import lingye.mapper.RecordMapper;
import lingye.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecordService {
    @Autowired
    private RecordMapper recordMapper;
    @Transactional
    public void add(Integer awardId, List<Integer> memberIds) {
        //此处推荐mybatis批量插入，不推荐下面的方法，因为比较耗时
        for (Integer memberId : memberIds){
            Record record = new Record();
            record.setAwardId(awardId);
            record.setMemberId(memberId);
            recordMapper.insertSelective(record);
        }
    }
    @Transactional
    public void deleteByMemberId(Integer id) {
        Record record = new Record();
        record.setMemberId(id);
        recordMapper.deleteByCondition(record);
    }

    @Transactional
    public void deleteByAwardId(Integer id) {
        Record record = new Record();
        record.setAwardId(id);
        recordMapper.deleteByCondition(record);
    }
}
