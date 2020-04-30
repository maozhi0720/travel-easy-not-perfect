package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.IUserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserService implements IUserService {
    private UserDao userDao = new UserDao();

    public  boolean registe(User user) throws Exception {
        //根据用户名查询
        User user1 = userDao.findByUsername(user);
        if (user1 !=null){
            //用户名存在
            return false;
        }

        //保存信息
        //2.1设置唯一code
        user.setCode(UuidUtil.getUuid());
        //2.2设置status状态
        user.setStatus("N");
        userDao.addUser(user);
        //发送激活邮件
        String content = "<a href='http://localhost/travel/user/active?code="+ user.getCode()+"'> "+user.getName()+"欢迎注册，请点击链接激活！【黑马旅游网】</a>";
        MailUtils mailUtils = new MailUtils();
        mailUtils.action(content,user.getEmail());
        return true;
    }

    @Override
    public boolean active(User code) {
        //根据code查询
        User code1 =  userDao.findByCode(code);
        if (code1.getUsername() == null){
            //code不存在
            System.out.println("null里的"+code1);
            return false;
        }
        //2.2设置status状态
        code.setStatus("Y");
        userDao.activeUser(code);
        return true;
    }

}
