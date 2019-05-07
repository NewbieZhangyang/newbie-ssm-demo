package com.newbie.dao;

import com.newbie.domain.User;

import java.util.List;

/**
 * 数据库操作对象：用户类
 */
public interface UserDAO {
    List<User> selectAll();
}
