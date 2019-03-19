package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProjectController {
    @RequestMapping(value = {"/common/projects"}, method = RequestMethod.GET)
    public String projects(){
        return "projects";
    }
}
