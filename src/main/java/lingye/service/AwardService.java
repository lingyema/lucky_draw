package lingye.service;

import lingye.mapper.AwardMapper;
import lingye.model.Award;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AwardService {
    @Autowired
    private AwardMapper awardMapper;
    public List<Award> query(Award award) {
        List<Award> awards = awardMapper.query(award);
        return awards;
    }

    @Transactional
    public void add(Award award) {
        awardMapper.insertSelective(award);
    }

    @Transactional
    public void update(Award award) {
        awardMapper.updateByPrimaryKeySelective(award);
    }

    @Transactional
    public void delete(Integer id) {
        awardMapper.deleteByPrimaryKey(id);
    }
}
