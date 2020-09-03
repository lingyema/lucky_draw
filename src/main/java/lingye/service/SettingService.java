package lingye.service;

import lingye.exception.BusinessException;
import lingye.mapper.SettingMapper;
import lingye.model.Award;
import lingye.model.Member;
import lingye.model.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SettingService {
    @Autowired
    private SettingMapper settingMapper;
    @Autowired
    private AwardService awardService;
    @Autowired
    private MerberService merberService;
    public Setting query(Integer id) {
        Setting query = new Setting();
        //id是用户id
        query.setUserId(id);
        //注册用户时，需要生成一个setting数据，是1对1的，如果没有生成则业务有问题
        Setting setting = settingMapper.selectOne(query);//根据id查询对应的setting
        if (setting == null)
            throw new BusinessException("SET001","用户设置信息出错");
        //查询奖品列表，人员列表设置到属性中
        //查询奖品列表，通过setting_id查询
        Award award = new Award();
        award.setSettingId(setting.getId());
        //此setting下的奖品种类
        List<Award> awards = awardService.query(award);
        setting.setAwards(awards);
        //查询人员列表，通过user_id查询
        Member member = new Member();
        member.setUserId(id);
        List<Member> members = merberService.query(member);
        setting.setMembers(members);
        return setting;
    }

    @Transactional
    public void add(Setting setting) {
        settingMapper.insertSelective(setting);
    }
    //spring事务设置：默认的传播方式为required，当前没有事务就创建事务，有就加入
    @Transactional
    public void update(Integer id, Integer batchNumber) {
        int num = settingMapper.updateByUserId(id,batchNumber);
    }
}
