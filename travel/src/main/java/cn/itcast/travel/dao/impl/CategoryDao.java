package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.ICategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDao implements ICategoryDao {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";
        List<Category> category = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
        return category;
    }
}
