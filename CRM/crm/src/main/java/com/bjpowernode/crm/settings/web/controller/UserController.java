package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.MD5Util;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 姜宝
 * 2021/4/12
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("settings/qx/user/toLogin")
    public String toLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String loginAct = null;
        String loginPwd = null;
        for (Cookie cookie : cookies) {
            if ("loginAct".equals(cookie.getName())) {
                loginAct = cookie.getValue();
            } else if ("loginPwd".equals(cookie.getName())) {
                loginPwd = cookie.getValue();
            }
        }
        if (loginAct != null && loginPwd != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("loginAct", loginAct);
            map.put("loginPwd", MD5Util.getMD5(loginPwd));
            User user = userService.selectUserByLoginActAndPwd(map);
            if (user == null) {
                return "settings/qx/user/login";
            } else if ("0".equals(user.getLockState() )) {
                return "settings/qx/user/login";
            } else if (user.getExpireTime().compareTo(new SimpleDateFormat("yyyy-MM--dd HH:mm:ss").format(new Date())) < 0) {
                return "settings/qx/user/login";
            }
            request.getSession().setAttribute(Contants.SESSION_USER, user);
            return "redirect:/workbench/index.do";
        } else {
            return "settings/qx/user/login";
        }
    }

    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody
    ReturnObject login(String loginAct, String loginPwd, String isRemPwd, HttpSession session, HttpServletResponse response) {
        ReturnObject returnObject = new ReturnObject();
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", MD5Util.getMD5(loginPwd));
        User user = userService.selectUserByLoginActAndPwd(map);
        if (user == null) {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码错误");
        } else if ("0".equals(user.getLockState())) {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("账号已经封禁");
        } else if (user.getExpireTime().compareTo(new SimpleDateFormat("yyyy-MM--dd HH:mm:ss").format(new Date())) < 0) {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_FAIL);
            returnObject.setMessage("账号已经过期");
        } else {
            returnObject.setCode(Contants.RETURNOBJECT_CODE_SUCCESS);
            //保存用户信息
            session.setAttribute(Contants.SESSION_USER, user);
            //判断是否选择了十天免登陆
            if ("true".equals(isRemPwd)) {
                Cookie c1 = new Cookie("loginAct", loginAct);
                Cookie c2 = new Cookie("loginPwd", loginPwd);
                c1.setMaxAge(10 * 24 * 60 * 60);
                c2.setMaxAge(10 * 24 * 60 * 60);
                //将cookie保存到浏览器中
                response.addCookie(c1);
                response.addCookie(c2);
            }
        }
        return returnObject;
    }

    @RequestMapping("settings/qx/user/logout.do")
    public String logout(HttpServletResponse response, HttpSession session) {
        //清空session
        session.invalidate();
        //清空cookie
        Cookie c1 = new Cookie("loginAct", null);
        Cookie c2 = new Cookie("loginPwd", null);
        c1.setMaxAge(0);
        c2.setMaxAge(0);
        response.addCookie(c1);
        response.addCookie(c2);
        return "redirect:/";
    }
}
