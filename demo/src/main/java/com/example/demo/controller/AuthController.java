package com.example.demo.controller;

import com.example.demo.form.SignInForm;
import com.example.demo.form.SignUpForm;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {
    @Autowired
    SignInForm signInForm;

    @Autowired
    SignUpForm signUpForm;

    @Autowired
    UsersService usersService;

    @RequestMapping(value = {"/signIn"}, method = RequestMethod.POST)
    public String signIn(HttpServletResponse resp, @RequestParam(name = "username") String username, @RequestParam(name = "pass") String password){
        SignInForm signInForm = SignInForm.builder()
                .login(username)
                .password(password)
                .build();

        String cookieValue = usersService.signIn(signInForm);
        if (cookieValue != null) {
            Cookie auth = new Cookie("auth", cookieValue);
            resp.addCookie(auth);
            return "/common/index";
        } else {
            return "signIn";
        }
    }
    @RequestMapping(value = {"/signUp"}, method = RequestMethod.POST)
    public String signUp(@RequestParam(name = "username") String username, @RequestParam(name = "pass") String password){
        SignUpForm signUpForm = SignUpForm.builder()
                .login(username)
                .password(password)
                .build();
        usersService.signUp(signUpForm);
        return "signIn";
    }

    @RequestMapping(value = { "/common/logout" }, method = RequestMethod.GET)
    public String signUp(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length ; i++) {
            if(cookies[i].getName().equals("auth")){
                cookie = cookies[i];
            }
        }
        usersService.delete(cookie.getValue());
        return "signUp";
    }
}
