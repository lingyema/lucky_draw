package lingye.mapper;

import lingye.base.BaseMapper;
import lingye.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}