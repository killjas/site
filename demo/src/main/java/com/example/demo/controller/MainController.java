package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
    // ​​​​​​​
    // Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);

        return "home";
    }
    @RequestMapping(value = { "/signIn" }, method = RequestMethod.GET)
    public String signIn() {
        return "signIn";
    }
    @RequestMapping(value = { "/editProfilePage" }, method = RequestMethod.GET)
    public String editProfilePage() {
        return "editProfilePage";
    }
    @RequestMapping(value = { "/newProject" }, method = RequestMethod.GET)
    public String newProject() {
        return "newproject";
    }
    @RequestMapping(value = { "/profile" }, method = RequestMethod.GET)
    public String profile() {
        return "profile";
    }
    @RequestMapping(value = { "/signUp" }, method = RequestMethod.GET)
    public String signUp() {
        return "signUp";
    }
}