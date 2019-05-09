package com.newbie.controller;

import com.newbie.domain.User;
import com.newbie.service.ITransactionService;
import com.newbie.service.impl.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * 测试：单数据库中的事务操作
 */
@Controller
public class TransactionController {
    @Resource
    ITransactionService transactionService;

    /**
     * 修改数据：没有增加事务控制
     */
    @RequestMapping("/notTransactionCommit")
    public String notTransactionCommit(Model model) {
        //执行数据库操作
        try {
            transactionService.notTransactionCommit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置向客户端返回的model数据 和 逻辑视图名称
        return this.setModel(model);
    }

    /**
     * 修改数据：使用注解的方式，增加事务控制
     */
    @RequestMapping("/annotationCommit")
    public String annotationCommit(Model model){
        try {
            transactionService.annotationCommit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置向客户端返回的model数据 和 逻辑视图名称
        return this.setModel(model);
    }

    /**
     * 修改数据：基于AOP切面的方式，增加事务控制
     */
    @RequestMapping("/aopAdviceCommit")
    public String aopAdviceCommit(Model model) {
        try {
            transactionService.aopAdviceCommit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置向客户端返回的model数据 和 逻辑视图名称
        return this.setModel(model);
    }

    /**
     * 重置数据：将账户余额 和 股票票数 重置到原始状态
     * @param model
     * @return
     */
    @RequestMapping("/resetData")
    public String resetData(Model model){
        transactionService.updateResetData();
        model.addAttribute("message","数据重置完成");
        return "showInfo";
    }
    /**
     * 设置向客户端返回的model数据 和 逻辑视图名称
     */
    public String setModel(Model model) {
        String message = "操作失败";
        message = transactionService.queryData();
        model.addAttribute("message", message);
        return "showInfo";
    }

}
