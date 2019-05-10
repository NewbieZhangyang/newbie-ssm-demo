package com.newbie.controller;

import com.newbie.service.IReadWriteSeparationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 测试：主从读写分离的实现
 */
@Controller
public class ReadWriteSeparationController {
    @Resource
    IReadWriteSeparationService readWriteSeparationService;

    /**
     * 向主库增加一个用户
     */
    @RequestMapping("/insertOneUser")
    public String insertOneUser(Model model) {
        String message = readWriteSeparationService.insertOneUser();
        model.addAttribute("message", message);
        return "showInfo";
    }


    /**
     * 使用非query方法，查询主库数据
     *
     * @return
     */
    @RequestMapping("/notQueryAllUser")
    public String notQueryAllUser(Model model) {
        String message = readWriteSeparationService.notQueryAllUser();
        model.addAttribute("message", message);
        return "showInfo";
    }

    /**
     * 使用query方法，查询从库数据
     *
     * @return
     */
    @RequestMapping("/queryAllUser")
    public String queryAllUser(Model model) {
        String message = readWriteSeparationService.queryAllUser();
        model.addAttribute("message", message);
        return "showInfo";
    }

}
