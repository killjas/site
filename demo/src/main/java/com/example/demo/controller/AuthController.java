package com.example.demo.controller;

import com.example.demo.form.SignInForm;
import com.example.demo.form.SignUpForm;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/signIn")
    public String signIn(HttpServletResponse resp, @RequestParam(name = "username") String username, @RequestParam(name = "pass") String password) {
        SignInForm signInForm = SignInForm.builder()
                .login(username)
                .password(password)
                .build();

        String cookieValue = usersService.signIn(signInForm);
        if (cookieValue != null) {
            Cookie auth = new Cookie("auth", cookieValue);
            resp.addCookie(auth);
            return "redirect:/home";
        } else {
            return "redirect:/signIn";
        }
    }
    @PostMapping("/signUp")
    public String signUp(@RequestParam(name = "username") String username, @RequestParam(name = "pass") String password) {
        SignUpForm signUpForm = SignUpForm.builder()
                .login(username)
                .password(password)
                .build();
        usersService.signUp(signUpForm);
        return "redirect:/signIn";
    }

    @GetMapping("/common/logout")
    public String signUp(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("auth")) {
                cookie = cookies[i];
            }
        }
        usersService.delete(cookie.getValue());
        return "redirect:/signUp";
    }
}
