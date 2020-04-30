package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface IUserDAo {
    /**
     * 根据username查询账户
     */
    public  User findByUsername(User user);
    /**
     * 添加用户
     */
    public  void addUser(User user);
    /**
     * 根据code查询用户
     */
    public User findByCode(User code);
    /**
     * 激活用户
     */
    public void activeUser(User user);

    User findByUsernameAndPassword(User user);
}
