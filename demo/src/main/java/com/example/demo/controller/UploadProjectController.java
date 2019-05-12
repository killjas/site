package com.example.demo.controller;

import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadProjectController {
    @Autowired
    UsersService usersService;


    @RequestMapping(value = "/newProject", method = RequestMethod.GET)
    public String upFileHandler() {
        return "newproject";
    }

    @RequestMapping(value = "/newProject", method = RequestMethod.POST)
    public String upload(HttpServletRequest request, Model model, @RequestParam("files") MultipartFile[] files, @RequestParam("projectName") String projectName) {
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie value : cookies) {
            if (value.getName().equals("auth")) {
                cookie = value;
            }
        }
        long id = usersService.findUserByCookie(cookie.getValue()).getId();
        StringBuilder fileNames = new StringBuilder();
        String uploadDirectory = System.getProperty("user.dir") + "/uploads" + "/" + projectName;
        File path = new File(uploadDirectory);
        path.mkdirs();
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename() + " ");
            try {
                Files.write(fileNameAndPath, file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        if (id != null) {
        usersService.addProject(id, projectName);
//        }
        model.addAttribute("msg", "Successfully uploaded files ");
        return "uploadResult";
    }
}
