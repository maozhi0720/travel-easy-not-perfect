package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface IRouteService {
    public PageBean<Route> pageQuery(int currentPage,int pageSize,int cid,String rname);
    public Route findOne(String  rid);
    public boolean findFa(int uid,int rid);

    public Integer  findFaCout(int rid);

    public void addFavorite(int rid, int uid,int count);
}
