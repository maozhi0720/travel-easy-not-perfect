package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface ILoginService  {
    User login(User user)throws Exception;
}
