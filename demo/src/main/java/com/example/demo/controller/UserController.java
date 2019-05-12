package com.example.demo.controller;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UsersService us;

    @GetMapping("/profile")
    public String user(HttpServletRequest req, Model model) {
        Cookie cookie = null;
        Cookie[] cookies = req.getCookies();
        for (Cookie value : cookies) {
            if (value.getName().equals("auth")) {
                cookie = value;
            }
        }
        User user = us.findUserByCookie(cookie.getValue());
        req.getParameter("userId");
        model.addAttribute("user", user);
        List<String> list=us.getUsersProjects(user.getId());
        model.addAttribute("projects", list);
        return "profile";
    }
}
