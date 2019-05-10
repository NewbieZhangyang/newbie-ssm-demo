package com.newbie.service;

/**
 * 单数据库中的事务操作测试
 */
public interface ITransactionService {
    void notTransactionCommit() throws Exception;
    void annotationCommit() throws Exception;
    void aopAdviceCommit() throws Exception;
    String ignoreQueryData();
    void updateResetData();
}
