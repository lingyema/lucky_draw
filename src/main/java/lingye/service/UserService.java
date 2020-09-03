package lingye.service;

import lingye.exception.ClientException;
import lingye.exception.SystemException;
import lingye.mapper.UserMapper;
import lingye.model.Setting;
import lingye.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class UserService {
    @Value("${user.head.local-path}")
    private String localPath;
    @Value("${user.head.remote-path}")
    private String remotePath;
    @Value("${user.head.filename}")
    private String fileName;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SettingService settingService;
    public User login(User user) {
//        User exist = userMapper.login(user);
//        if (exist == null){
//            throw new ClientException("USR001","用户名密码校验失败");
//        }
        User query = new User();
        query.setUsername(user.getUsername());
        User exist = userMapper.selectOne(query);
        if (exist == null)
            throw new ClientException("USR001","该用户不存在");
        if (!exist.getPassword().equals(user.getPassword()))
            throw new ClientException("USR002","用户名密码校验失败");
        return exist;
    }

    //remotePath远程访问路径，http路径
    //Transactional事务，默认配置：事务
    @Transactional
    public void register(User user, MultipartFile headFile) {
        //根据用户名查询已有用户信息，存在不允许注册
        User query = new User();
        query.setUsername(user.getUsername());
        User exist = userMapper.selectOne(query);
        if (exist != null)
            throw new ClientException("USR003","用户已存在");
        //不存在，注册（插入）用户信息
        String path = "/"+user.getUsername()+"/"+fileName;
        user.setHead(remotePath+path);
        userMapper.insertSelective(user);//插入成功之后，自增主键通过mybatis的selectkey返回对象
        //注册用户时，创建setting数据
        Setting setting = new Setting();
        setting.setUserId(user.getId());
        setting.setBatchNumber(8);
        settingService.add(setting);

        //保存头像到本地
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            try {
                File dir = new File(localPath+"/"+user.getUsername());
                dir.mkdirs();
                fos = new FileOutputStream(localPath+path);
                bos = new BufferedOutputStream(fos);
                bos.write(headFile.getBytes());
                //缓冲输出流，flush一下
                bos.flush();
            }  finally {
                if (bos != null)
                    bos.close();
                if (fos!=null)
                    fos.close();
            }
        } catch (IOException e) {
            //打印捕获异常，抛出自定义异常，统一异常拦截器打印自定义异常
//            e.printStackTrace();
//            throw new SystemException("USR004","用户注册失败：头像上传错误");
            throw new SystemException("USR004","用户注册失败：头像上传错误",e);
        }
    }
}
