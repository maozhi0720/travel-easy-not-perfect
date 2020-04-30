package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.RouteDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.IRouteService;

import java.util.List;

public class RouteService implements IRouteService {
    private RouteDao routeDao = new RouteDao();
    @Override
    public PageBean<Route> pageQuery(int currentPage, int pageSize, int cid,String rname) {

        //1.封装bean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount = routeDao.findCount(cid,rname);
        pb.setTotalCount(totalCount);
        //设置当前页数显示的集合
        int start = (currentPage - 1) * pageSize;
        List<Route> list = routeDao.findAll(cid,start,pageSize,rname);

        pb.setList(list);
        //设置总页数
        int totalPage = totalCount % pageSize == 0 ? totalCount/pageSize : (totalCount/pageSize) +1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public Route findOne(String rid) {
        int rid_int = Integer.parseInt(rid);
        Route routeOne = routeDao.findOne(rid_int);
        List<RouteImg> imglist = routeDao.findOneImg(rid_int);
        routeOne.setRouteImgList(imglist);
        Seller sellers = routeDao.findSeller(routeOne.getSid());
        routeOne.setSeller(sellers);
        return routeOne;
    }

    @Override
    public boolean findFa(int uid, int rid) {

        return routeDao.findFa(uid,rid);
    }

    @Override
    public Integer findFaCout(int rid) {
        return routeDao.findFaCount(rid);
    }

    @Override
    public void addFavorite(int rid, int uid,int count) {
        routeDao.addFavorite(rid,uid,count);
    }
}
