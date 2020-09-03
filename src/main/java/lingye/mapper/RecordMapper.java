package lingye.mapper;

import lingye.base.BaseMapper;
import lingye.model.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    void deleteByCondition(Record record);
}