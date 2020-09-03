package lingye.service;

import lingye.mapper.MemberMapper;
import lingye.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MerberService {
    @Autowired
    private MemberMapper memberMapper;
    public List<Member> query(Member member) {
        return memberMapper.selectByCondition(member);
    }

    @Transactional
    public void add(Member member) {
        memberMapper.insertSelective(member);
    }

    @Transactional
    public void update(Member member) {
        memberMapper.updateByPrimaryKeySelective(member);
    }

    public void delete(Integer id) {
        memberMapper.deleteByPrimaryKey(id);
    }
}
