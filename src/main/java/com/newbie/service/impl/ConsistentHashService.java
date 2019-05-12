package com.newbie.service.impl;

import com.newbie.dao.CustomerMapper;
import com.newbie.domain.Customer;
import com.newbie.domain.CustomerExample;
import com.newbie.domain.UserAccount;
import com.newbie.service.IConsistentHashService;
import com.newbie.util.dbMultipleRouting.DynamicDataSourceHolder;
import com.newbie.util.dbMultipleRouting.annotation.AnnotationDBSourceKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * 使用一致性hash的方式，实现分库插入和查询
 */
@Service
public class ConsistentHashService implements IConsistentHashService {
    @Resource
    private CustomerMapper customerMapper;
    /**
     * 用于记录每次操作的客户信息，以方便查询和插入操作
     */
    private List<Customer> customerList = new ArrayList<Customer>();
    private  int countNumber = 10;      //生成10个客户

    /**
     * 批量生成一写用户，保存到List集合，用于插入数据库 和 查询数据库
     * @return
     */
    public void initCustomerList(String name) {
        for (int i = 0; i < countNumber; i++) {
            Customer customer = new Customer();
            String idcardNo = getIdno();    //生成身份证号
            customer.setId(idcardNo);
            customer.setIdcardNo(idcardNo);
            customer.setName(name + "-" + (countNumber - i));
            customerList.add(customer);
        }
    }

    /**
     * 从客户集合中，随机抽取10个客户，用来查询数据库
     */
    public List<Customer> getTenCustomer(){
        List<Customer> customers = new ArrayList<Customer>();
        //设置要随机访问的客户总数
        int tmpCoustomerNum = customerList.size() < countNumber ? customerList.size() : countNumber;
        for (int i = 0; i < tmpCoustomerNum; i++) {
            int randmoIndex = new Random().nextInt(customerList.size());
            Customer customer = customerList.get(randmoIndex);
            customers.add(customer);
        }
        return customers;
    }

    /**
     * 使用随机数，生成身份证号ID
     * @return
     */
    public String getIdno() {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 18; i++) {
            int random = new Random().nextInt(10);
            if (random == 0) {
                random = 4;
            }
            sb.append(random);
        }
        return sb.toString();
    }

    /**
     * 向主库中，增加客户信息，插入十条数据，默认姓名为"简小六"
     * @return
     */
    @AnnotationDBSourceKey("master")
    public String addConstomer() {
        String message = "<p>插入结束</p>";
        message += "<p>插入信息如下：客户姓名 , 身份证号 , 原始数据源 , 最终数据源</p>";
        //向属性customerlist中，添加客户信息
        String name = "简小七";
        initCustomerList(name);
        int customerListSize = customerList.size();
        //向数据库中插入数据
        Iterator<Customer> iterator = customerList.iterator();
        while (iterator.hasNext()){
            Customer customer = iterator.next();
            DynamicDataSourceHolder.setConsistentHashKey(customer.getIdcardNo());   //设置用于一致性hash计算的key，以此来动态修改操作的数据源
            customerMapper.insertSelective(customer);       //执行数据库插入操作
            message += "<p>"+customer.getName()+" , "+customer.getIdcardNo()+" , "+DynamicDataSourceHolder.getDataSourceKey()+" , "+DynamicDataSourceHolder.getDataSourceFinalKey();
        }
        return message;
    }

    /**
     * 从主库库中查询客户信息
     */
    @AnnotationDBSourceKey("master")
    public String queryCustomerFromMater() {
        return queryData();
    }

    /**
     * 从slave库中查询客户信息
     */
    @AnnotationDBSourceKey("slave")
    public String queryCustomer() {
       return queryData();
    }




    /**
     * 将查询数据的业务抽离成公共方法
     */
    public String queryData(){
        String message = "<p>查询结束：</p>";
        message += "<p>查询信息如下：客户姓名 , 身份证号 , 原始数据源 , 最终数据源</p>";
        //从数据库中查询数据
        Iterator<Customer> iterator = getTenCustomer().iterator();
        while (iterator.hasNext()){
            Customer customer = iterator.next();
            String idcardNo = customer.getIdcardNo();
            CustomerExample example = new CustomerExample();
            example.createCriteria().andIdcardNoEqualTo(idcardNo);
            DynamicDataSourceHolder.setConsistentHashKey(idcardNo); //设置用于一致性hash计算的key，以此来动态修改操作的数据源
            customerMapper.selectByExample(example);                //执行数据库插入操作
            message += "<p>"+customer.getName()+" , "+customer.getIdcardNo()+" , "+DynamicDataSourceHolder.getDataSourceKey()+" , "+DynamicDataSourceHolder.getDataSourceFinalKey();
        }
        return message;
    }

}
