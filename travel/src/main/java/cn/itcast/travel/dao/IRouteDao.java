package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;

import java.util.List;

public interface IRouteDao {
    /**
     * 查询总记录条数
     */
    public Integer findCount(Integer cid,String rname);
    /**
     * 查询路线集合
     */
    public List<Route> findAll(Integer cid,Integer start, Integer pageSize,String rname);
    /**
     * 查询1条旅游路线（rid）
     */
    public Route findOne(int rid);
    /**
     *根据rid查询这条线路的所有图片
     */
    public List<RouteImg> findOneImg(int rid);
    /**
     *根据sid查询卖家
     */
    public Seller findSeller(int sid);
    public boolean findFa(int uid,int rid);

    public Integer findFaCount(int rid);

    public void addFavorite(int rid, int uid,int count);
}
