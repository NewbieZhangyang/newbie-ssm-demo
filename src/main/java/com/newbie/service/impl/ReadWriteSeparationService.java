package com.newbie.service.impl;

import com.newbie.dao.UserMapper;
import com.newbie.domain.User;
import com.newbie.service.IReadWriteSeparationService;
import com.newbie.util.dbMultipleRouting.DynamicDataSourceHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * 主从读写分离的实现
 */
@Service
public class ReadWriteSeparationService implements IReadWriteSeparationService {
    @Resource
    private UserMapper userMapper;

    /**
     * 向主库增加一个用户
     */
    public String insertOneUser() {
        String message = "插入完成";
        message += "<p>当前线程数据源：key = "+ DynamicDataSourceHolder.getDataSourceKey()+"</p>";
        User user = new User();
        String id = String.valueOf(System.currentTimeMillis());
        user.setId(id);
        user.setUsername("我是新插入的用户");
        userMapper.insertSelective(user);
        return message;
    }

    /**
     * 使用非query方法，查询主库数据
     * @return
     */
    public String notQueryAllUser() {
        String message = "<p>查询成功<p>";
        message += "<p>当前线程数据源：key = "+ DynamicDataSourceHolder.getDataSourceKey()+"</p>";
        //查询数据库中的所有用户信息
        List<User> users = userMapper.selectByExample(null);
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            message += "<p>用户ID : "+user.getId()+" , 用户名 : "+user.getUsername()+" , 数据来源："+user.getRemark()+"</p>";
        }
        System.out.println("================== Service.notQueryAllUser() : 当前线程数据源 = "+DynamicDataSourceHolder.getDataSourceKey());
        System.out.println("================== Service.notQueryAllUser() : 返回信息 = "+message);
        return message;
    }

    /**
     * 使用query方法，查询从库数据
     * @return
     */
    public String queryAllUser() {
        String message = "<p>查询成功<p>";
        message += "<p>当前线程数据源：key = "+ DynamicDataSourceHolder.getDataSourceKey()+"</p>";
        //查询数据库中的所有用户信息
        List<User> users = userMapper.selectByExample(null);
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()){
            User user = iterator.next();
            message += "<p>用户ID : "+user.getId()+" , 用户名 : "+user.getUsername()+" , 数据来源："+user.getRemark()+"</p>";
        }
        System.out.println("================== Service.notQueryAllUser() : 当前线程数据源 = "+DynamicDataSourceHolder.getDataSourceKey());
        System.out.println("================== Service.queryAllUser() : 返回信息 = "+message);
        return message;
    }
}
