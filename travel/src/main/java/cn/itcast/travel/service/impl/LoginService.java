package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.ILoginService;

public class LoginService implements ILoginService {
    private UserDao userDao = new UserDao();

    public  User login(User user) throws Exception {
    //根据用户名查询
    User user1 = userDao.findByUsernameAndPassword(user);
        if (user1.getUsername() == null){
        //用户名或密码错误
        return null;
    }
        return user1;
}
}
