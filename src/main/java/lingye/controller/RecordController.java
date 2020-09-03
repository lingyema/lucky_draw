package lingye.controller;

import lingye.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;
    //添加某奖项的获奖名单
    @PostMapping("/add/{awardId}")
    public Object add(@PathVariable Integer awardId, @RequestBody List<Integer> memberIds){
        recordService.add(awardId,memberIds);
        return null;
    }
    @GetMapping("/delete/member")
    public Object deleteByMemberId(Integer id){//业务上一个人只能抽一个奖，用memberId删，如果可以获得多个奖
        //通过memberId+awardId删
        recordService.deleteByMemberId(id);
        return null;
    }
    @GetMapping("/delete/award")
    public Object deleteByAwardId(Integer id){
        recordService.deleteByAwardId(id);
        return null;
    }
}
