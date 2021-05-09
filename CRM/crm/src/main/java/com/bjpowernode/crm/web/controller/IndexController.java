package com.bjpowernode.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 姜宝
 * 2021/4/9
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
