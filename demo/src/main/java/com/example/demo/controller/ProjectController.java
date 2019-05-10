package com.example.demo.controller;

import com.example.demo.model.Project;
import com.example.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @GetMapping("/projects")
    public String projects(HttpServletRequest req){
        List<Project> projects= projectService.findAll();
        req.setAttribute("projects", projects);
        return "allproject";
    }
    @PostMapping("/addProject")
    public String addProject(){
        
        return null;
    }
}
