package lingye.controller;

import lingye.model.Award;
import lingye.model.User;
import lingye.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/award")
public class AwardController {
    @Autowired
    private AwardService awardService;
    @PostMapping("/add")
    public Object add(@RequestBody Award award, HttpSession session){//插入时，没有id字段
        User user = (User) session.getAttribute("user");
        award.setSettingId(user.getSettingId());//将新增的奖项与抽奖设置id对应起来
        awardService.add(award);
        return null;
//        return award.getId();//数据库插入时自动设置id为自增主键，需将id返回给前端，否则有bug；须于前端对应
    }
    @PostMapping("/update")
    public Object update(@RequestBody Award award){//修改，根据id修改
        awardService.update(award);
        return null;
    }
    @GetMapping("/delete/{id}")
    public Object delete(@PathVariable Integer id){//@PathVariable中的值绑定路径变量，若无值，绑定变量名
        //Integer id1 = Integer.parseInt(id);

        awardService.delete(id);
        return null;
    }
}
