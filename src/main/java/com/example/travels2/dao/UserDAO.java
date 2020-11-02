package com.example.travels2.dao;

import com.example.travels2.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDAO {
    //注册用户
    void save(User user);
    //根据用户名查询用户
    User findByUsername(String username);
}
