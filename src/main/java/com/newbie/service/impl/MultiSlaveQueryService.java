package com.newbie.service.impl;

import com.newbie.dao.UserAccountMapper;
import com.newbie.domain.UserAccount;
import com.newbie.service.IMultiSlaveQueryService;
import com.newbie.util.dbMultipleRouting.DynamicDataSource;
import com.newbie.util.dbMultipleRouting.DynamicDataSourceHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * 测试：采用（基数 % 从库数）求模的方式，来动态请求不同的从库
 */
@Service
public class MultiSlaveQueryService implements IMultiSlaveQueryService {
    @Resource
    private UserAccountMapper userAccountMapper;

    /**
     * 查询主库时，直接去主库查询，不进行分库计算
     * @return
     */
    public String queryMaster() {
        String message = queryData("master");
        return message;
    }

    /**
     * 查询从库时，随机生成一个基数，然后根据基数和从库总数，来计算要查询哪个分库
     * @return
     */
    public String querySlave() {
        String message = queryData("slave");
        return message;
    }

    /**
     * 查询数据库
     * @param key
     * @return
     */
    public String queryData(String key){
        String message = "<p>查询结束<p>";
        //查询十次数据库
        for (int i = 1; i <= 10; i++){
            int baseNumber = new Random().nextInt(20);
            DynamicDataSourceHolder.setDataSourceKey(key);
            DynamicDataSourceHolder.setBaseNumber(baseNumber);
            List<UserAccount> userAccountList = userAccountMapper.selectByExample(null);
            message += "<p>查询次数 = "+i+" ,基数 = "+baseNumber+" ,原key = "+ key+" , 最终key = "+ DynamicDataSourceHolder.getDataSourceKey()+"</p>";
            Iterator<UserAccount> iterator = userAccountList.iterator();
            while (iterator.hasNext()){
                UserAccount userAccount = iterator.next();
                message += "<p>用户ID : "+userAccount.getAccountId()+" , 账户余额 : "+userAccount.getBalance()+" , 数据来源："+userAccount.getRemark()+"</p>";
            }
            message += "<hr/>";
        }
        return message;
    }
}
