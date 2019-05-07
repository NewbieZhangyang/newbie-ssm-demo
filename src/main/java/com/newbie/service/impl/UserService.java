package com.newbie.service.impl;

import com.newbie.dao.UserDAO;
import com.newbie.domain.User;
import com.newbie.service.IUserService;
import com.newbie.util.DynamicDataSourceHolder;
import com.newbie.util.annotation.AnnotationDBSourceKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class UserService implements IUserService {
    @Resource
    private UserDAO userDAO;

    /**
     * 通过设置：DynamicDataSourceHolder.setDataSourceKey(dataSourceKey);的方式，来动态设置数据源
     */
    public List<User> searchAllUser(String dataSourceKey) {
        if(dataSourceKey == null){
            dataSourceKey = "master";
        }
        DynamicDataSourceHolder.setDataSourceKey(dataSourceKey);
        return userDAO.selectAll();
    }

    /*
     * 以下三个方法：searchMaster()、searchSlave1()、searchSlave2()
     * 使用自定义注解的方式，来动态设置数据源
     */
    @AnnotationDBSourceKey("master")
    public List<User> searchMaster() {
        return userDAO.selectAll();
    }
    @AnnotationDBSourceKey("slave1")
    public List<User> searchSlave1() {
        return userDAO.selectAll();
    }
    @AnnotationDBSourceKey("slave2")
    public List<User> searchSlave2() {
        return userDAO.selectAll();
    }
}
