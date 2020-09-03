package lingye.mapper;

import lingye.base.BaseMapper;
import lingye.model.Setting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SettingMapper extends BaseMapper<Setting> {
    //mybatis传入多个参数时，需使用@Param并指定名称,xml中引用@Param中的值为变量值
    int updateByUserId(@Param("userId") Integer id, @Param("batchNumber") Integer batchNumber);
}