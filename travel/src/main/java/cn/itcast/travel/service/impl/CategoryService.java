package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.ICategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryService implements ICategoryService {
    private CategoryDao categoryDao = new CategoryDao();
    @Override
    public List<Category> findAll() {
        //1从redis中查询
        //1.1获取jedis客户端
        Jedis jedisUtil = JedisUtil.getJedis();
        //1.2使用sortedset排序查询（没有cid)
        Set<String> categorys = jedisUtil.zrange("category", 0, -1);
        //1.2使用sortedset排序查询所有内容
        Set<Tuple> tuples = jedisUtil.zrangeWithScores("category", 0, -1);
        List<Category> cs = null;
        //2判断查询结果是否为空
        if(categorys == null || categorys.size() == 0){
            //redis中无数据，查询数据库

            cs = categoryDao.findAll();
            //写入redis
            for (int i = 0; i < cs.size();i++){
                jedisUtil.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else {
            //redis中有数据
            cs = new ArrayList<Category>();
            for (Tuple tuple : tuples) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);
            }
        }
       return cs;
    }
}
