package cn.itcast.travel.dao.impl;
import cn.itcast.travel.dao.IUserDAo;
import cn.itcast.travel.domain.User;

import cn.itcast.travel.service.impl.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import static cn.itcast.travel.util.JDBCUtils.*;

public class UserDao implements IUserDAo {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
    @Override
    public User findByUsername(User username) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username.getUsername());
        } catch (DataAccessException e) {

        }
        return user;
    }

    @Override
    public void addUser(User user) {
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)"+"" +
                "values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,user.getUsername(), user.getPassword(),user.getName(), user.getBirthday(),user.getSex(), user.getTelephone(),user.getEmail(), user.getStatus(), user.getCode());
    }

    @Override
    public User findByCode(User code) {
        try {
            String sql = "select * from tab_user where code = ?";
            code = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code.getCode());

        } catch (DataAccessException e) {

        }
        return code;
    }

    @Override
    public void activeUser(User user) {
        String sql = "update tab_user set status = ? where code = ?";
        jdbcTemplate.update(sql,user.getStatus(),user.getCode());
    }

    @Override
    public User findByUsernameAndPassword(User user) {
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(),user.getPassword());
        } catch (DataAccessException e) {

        }
        return user;
    }
}
