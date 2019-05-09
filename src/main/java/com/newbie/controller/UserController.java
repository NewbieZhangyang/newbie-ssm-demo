package com.newbie.controller;

import com.newbie.domain.User;
import com.newbie.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测试：访问多数据源
 * 控制器：接收客户端请求，处理后将结果响应给客户
 */
@Controller
public class UserController {
    @Resource
    private IUserService userService;

    /**
     * 通过设置：DynamicDataSourceHolder.setDataSourceKey(dataSourceKey);的方式，来动态设置数据源
     */
    @RequestMapping("/searchAllUser")
    public String searchAllUser(Model model, String dataSourceKey){
        List<User> users = userService.searchAllUser(dataSourceKey);
        model.addAttribute("message","dataSourceKey = "+dataSourceKey);
        model.addAttribute("user",users.get(0));
        return "showInfo";
    }

    /*
     * 以下三个方法：searchMaster()、searchSlave1()、searchSlave2()
     * 使用自定义注解的方式，来动态设置数据源
     */
    @RequestMapping("/searchMaster")
    public String searchMaster(Model model){
        List<User> users = userService.searchMaster();
        model.addAttribute("message","dataSourceKey = master , method : searchMaster()");
        model.addAttribute("user",users.get(0));
        return "showInfo";
    }
    @RequestMapping("/searchSlave1")
    public String searchSlave1(Model model){
        List<User> users = userService.searchSlave1();
        model.addAttribute("message","dataSourceKey = slave1 , method : searchSlave1()");
        model.addAttribute("user",users.get(0));
        return "showInfo";
    }
    @RequestMapping("/searchSlave2")
    public String searchSlave2(Model model){
        List<User> users = userService.searchSlave2();
        model.addAttribute("message","dataSourceKey = slave2 , method : searchSlave2()");
        model.addAttribute("user",users.get(0));
        return "showInfo";
    }
}
