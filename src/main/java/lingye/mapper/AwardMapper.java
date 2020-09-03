package lingye.mapper;

import lingye.base.BaseMapper;
import lingye.model.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AwardMapper extends BaseMapper<Award> {
    List<Award> query(Award award);
}