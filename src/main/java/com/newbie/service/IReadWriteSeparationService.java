package com.newbie.service;

/**
 * 主从读写分离的实现
 */
public interface IReadWriteSeparationService {
    String insertOneUser();
    String notQueryAllUser();
    String queryAllUser();
}
