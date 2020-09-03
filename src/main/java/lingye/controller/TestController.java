package lingye.controller;

import lingye.mapper.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//返回体都是JSON格式
@RequestMapping("/test")
public class TestController {
    @Autowired
    private Test test;
    @GetMapping("1")
    public Object test1(){
        return test.query(1);
    }
}
