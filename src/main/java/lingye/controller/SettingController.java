package lingye.controller;

import lingye.model.Setting;
import lingye.model.User;
import lingye.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
//设置每次抽奖可以有多少个人一起抽
@RestController
@RequestMapping("/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;
    //当前用户设置的每次抽奖人数
    @GetMapping("/query")
    public Object query(HttpSession session){
        //登陆后才能访问的接口，从会话中获取用户信息
        User user = (User) session.getAttribute("user");
        Setting setting = settingService.query(user.getId());
        setting.setUser(user);
        return setting;
    }
    //更新抽奖人数
    @GetMapping("/update")
    //batchNumber：对应抽奖设置页面中，点每次抽奖人数下拉菜单切换时修改
    public Object update(Integer batchNumber,HttpSession session){
        User user = (User) session.getAttribute("user");
        settingService.update(user.getId(),batchNumber);
        return null;
    }
}
