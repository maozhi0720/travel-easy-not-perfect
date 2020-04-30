package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface IUserService {
    /**
     * 根据username查询账户,注册用户
     */
    public boolean registe(User user) throws Exception;

    public boolean active(User code);
}
