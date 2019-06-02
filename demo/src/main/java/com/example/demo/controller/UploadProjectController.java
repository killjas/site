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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UploadProjectController {
    @Autowired
    UsersService usersService;


    @RequestMapping(value = "/newProject", method = RequestMethod.GET)
    public String upFileHandler() {
        return "newproject";
    }

    @RequestMapping(value = "/newProject", method = RequestMethod.POST)
    public String upload(HttpServletRequest request, Model model, @RequestParam("files") MultipartFile[] files, @RequestParam("projectName") String projectName) throws IOException {
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie value : cookies) {
            if (value.getName().equals("auth")) {
                cookie = value;
            }
        }
        long id = 6;
        StringBuilder fileNames = new StringBuilder();
        String uploadDirectory = System.getProperty("user.dir") + "/uploads" + "/" + projectName;
        File path = new File(uploadDirectory);
        path.mkdirs();

        List<File> uploadedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
            File serverFile = new File(String.valueOf(fileNameAndPath));

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(file.getBytes());
            stream.close();
            uploadedFiles.add(serverFile);
            System.out.println("Write file: " + serverFile);
        }
//        if (id != null) {
        usersService.addProject(id, projectName);
//        }
        model.addAttribute("msg", "Successfully uploaded files ");
        return "uploadResult";
    }
}
