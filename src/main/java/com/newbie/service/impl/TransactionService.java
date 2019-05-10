package com.newbie.service.impl;

import com.newbie.dao.UserAccountMapper;
import com.newbie.dao.UserStockMapper;
import com.newbie.domain.UserAccount;
import com.newbie.domain.UserStock;
import com.newbie.service.ITransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.annotation.Resource;
import javax.transaction.Transactional;

/**
 * 单数据库中的事务操作测试
 */
@Service
public class TransactionService implements ITransactionService {
    @Resource
    private UserAccountMapper userAccountMapper;
    @Resource
    private UserStockMapper userStockMapper;

    /**
     * 没有增加事务控制
     */
    public void notTransactionCommit() throws Exception{
        updateDBData();
    }

    /**
     * 使用注解的方式，增加事务控制
     */
    @Transactional(rollbackOn = Exception.class)
    public void annotationCommit() throws Exception{
        updateDBData();
    }

    /**
     * 基于AOP切面的方式，增加事务控制
     */
    public void aopAdviceCommit() throws Exception{
        updateDBData();

    }

    /**
     * 查询用户的账户余额 和 股票票数
     * @return
     */
    public String ignoreQueryData(){
        String accountId = "6222-0001"; //账户Id
        String stockId = "AB-01";       //股票ID
        //查询用户的账户余额 和 股票票数
        UserAccount account = userAccountMapper.selectByPrimaryKey(accountId);
        UserStock stock = userStockMapper.selectByPrimaryKey(stockId);
        String message = "<p>查询成功<p>";
        message += "<p>账户Id : "+account.getAccountId()+" , 余额 : "+account.getBalance()+"</p>";
        message += "<p>股票Id："+stock.getStockId()+" , 股票数 ："+stock.getCountNum()+"</p>";
        System.out.println("================== service : message = "+message);
        return message;
    }

    /**
     * 重置数据：将账户余额 和 股票票数 重置到原始状态
     */
    public void updateResetData(){
        UserAccount account = new UserAccount();
        account.setAccountId("6222-0001");
        account.setBalance(100);
        UserStock stock = new UserStock();
        stock.setStockId("AB-01");
        stock.setCountNum(0);
        userAccountMapper.updateByPrimaryKeySelective(account);
        userStockMapper.updateByPrimaryKeySelective(stock);
    }

    /**
     * 修改数据
     * 第一步：查询消费前，用户的账户余额 和 股票票数
     * 第二步：修改账户余额，余额减去 20元
     * 第三步：修改股票票数，票数增加 20股
     */
    public void updateDBData() throws Exception{
        String accountId = "6222-0001"; //账户Id
        String stockId = "AB-01";       //股票ID
        int minusMoney = 20;            //消费金额
        //查询消费前，用户的账户余额 和 股票票数
        UserAccount account = userAccountMapper.selectByPrimaryKey(accountId);
        UserStock stock = userStockMapper.selectByPrimaryKey(stockId);
        //修改账户余额 和 股票票数
        account.setBalance(account.getBalance() - minusMoney);
        stock.setCountNum(stock.getCountNum() + minusMoney);
        //执行数据库操作，完成修改
        userAccountMapper.updateByPrimaryKey(account);
        if(true){
            throw new Exception("出现了异常，程序中断了，应该将之前的数据回退");
            //  Spring事务控制，默认情况下监听的是运行时异常及其子类异常
            // 不过可以在配置事务时，修改事务监听的异常类（rollbackOn = Exception.class）
            //int rs = 1/0;
        }
        userStockMapper.updateByPrimaryKey(stock);
    }
}
