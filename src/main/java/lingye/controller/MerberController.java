package lingye.controller;

import lingye.model.Member;
import lingye.model.User;
import lingye.service.MerberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
public class MerberController {
    @Autowired
    private MerberService merberService;
    @PostMapping("/add")
    public Object add(@RequestBody Member member, HttpSession session){
        User user = (User) session.getAttribute("user");
        member.setUserId(user.getId());
        merberService.add(member);
        return null;
    }
    @PostMapping("/update")
    public Object update(@RequestBody Member member){
        merberService.update(member);
        return null;
    }
    @GetMapping("/delete/{id}")
    public Object delete(@PathVariable Integer id){
        merberService.delete(id);
        return null;
    }
}
