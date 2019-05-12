package com.newbie.controller;

import com.newbie.domain.Customer;
import com.newbie.service.IConsistentHashService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class ConsistentHashController {
    @Resource
    IConsistentHashService consistentHashService;

    /**
     * 插入十条数据，默认姓名为"简小六"
     */
    @RequestMapping("/addCustomer")
    public String addCustomer(Model model) {
        String message = consistentHashService.addConstomer();
        model.addAttribute("message", message);
        return "showQuery";
    }

    /**
     * 从主库库中查询客户信息
     */
    @RequestMapping("queryCustomerFromMater")
    public String queryCustomerFromMater(Model model){
        String message = consistentHashService.queryCustomerFromMater();
        model.addAttribute("message", message);
        return "showQuery";
    }

    /**
     * 从slave库中查询客户数据
     */
    @RequestMapping("queryCustomer")
    public String queryCustomer(Model model){
        String message = consistentHashService.queryCustomer();
        model.addAttribute("message", message);
        return "showQuery";
    }

}
