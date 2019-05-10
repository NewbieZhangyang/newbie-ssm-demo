package com.newbie.controller;

import com.newbie.service.IMultiSlaveQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 测试：采用（基数 % 从库数）求模的方式，来动态请求不同的从库
 */
@Controller
public class MultiSlaveQueryController {
    @Resource
    private IMultiSlaveQueryService multiSlaveQueryService;

    /**
     * 查询主库时，直接查询
     */
    @RequestMapping("/queryMaster")
    public String queryMaster(Model model) {
        String message = multiSlaveQueryService.queryMaster();
        model.addAttribute("message", message);
        return "showQuery";
    }

    /**
     * 查询从库时，求模后查询对应的从库
     */
    @RequestMapping("/querySlave")
    public String querySlave(Model model) {
        String message = multiSlaveQueryService.querySlave();
        model.addAttribute("message", message);
        return "showQuery";
    }

}
