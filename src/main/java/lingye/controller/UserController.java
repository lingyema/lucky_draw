package lingye.controller;

import lingye.model.Setting;
import lingye.model.User;
import lingye.service.SettingService;
import lingye.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private SettingService settingService;
    @PostMapping("/login")
    public Object login(@RequestBody User user, HttpServletRequest req){
        //如果用户名密码校验失败，在service中抛异常，此处不做处理，exist一定不为空
        User exist = userService.login(user);//此处返回一个存在的用户信息
        Setting setting = settingService.query(exist.getId());//此处返回用户对应的设置，奖品，参与人员信息
        exist.setSettingId(setting.getId());
        HttpSession session = req.getSession();
        session.setAttribute("user",exist);
        return null;
    }
    //@RequestPart：接受的是文件
    //headFile上传的头像：1.保存在本地文件夹（web服务器需要加载到这个头像）2.url存放在注册用户的head字段
    //注册的时候需要生成一个setting
    @PostMapping("/register")
    public Object register(User user,
                           @RequestPart(value = "headFile",required = false) MultipartFile headFile){
        userService.register(user,headFile);
        return null;
    }
    @GetMapping("/logout")
    public void logout(HttpSession session){
        session.removeAttribute("user");
    }

}
