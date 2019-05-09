package com.newbie.service;


import com.newbie.domain.User;

import java.util.List;


public interface IUserService {
    List<User> searchAllUser(String dataSourceKey);

    List<User> searchMaster();
    List<User> searchSlave1();
    List<User> searchSlave2();
}
