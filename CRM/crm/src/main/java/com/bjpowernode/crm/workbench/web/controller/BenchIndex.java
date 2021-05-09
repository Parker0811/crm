package com.bjpowernode.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 姜宝
 * 2021/4/12
 */
@Controller
public class BenchIndex {
    @RequestMapping("workbench/index")
    public String benchIndex(){
        return "workbench/index";
    }

    @RequestMapping("/workbench/main/index.do")
    public String index(){
        return "workbench/main/index";
    }
}
