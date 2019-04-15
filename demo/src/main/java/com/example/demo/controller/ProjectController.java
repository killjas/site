package com.example.demo.controller;

import com.example.demo.form.UploadForm;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    ServletContext servletContext;

    @Autowired
    UsersService usersService;

    @RequestMapping(value = {"/projects"}, method = RequestMethod.GET)
    public String projects(Model model) {
        String name = "kek";
        model.addAttribute("name", name);
        return "projects";
    }


    @RequestMapping(value = "/uploadProject", method = RequestMethod.GET)
    public String upFileHandler() {
        return "uploadFilePage";
    }

    @RequestMapping(value = "/uploadProject", method = RequestMethod.POST)
    public String upload(HttpServletRequest request, Model model, @RequestParam("files") MultipartFile[] files, @RequestParam("projectName") String projectName) {
        Cookie cookie[] = request.getCookies();
        Long id = null;
        for (Cookie cookie1 : cookie) {
            if (cookie1.getName().equals("auth")) {
                id = usersService.findUserIdByCookie(cookie1.getValue());
            }
        }
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
        if (id != null) {
            usersService.addProject(id, projectName);
            model.addAttribute("msg", "Successfully uploaded files ");
        }
        return "uploadResult";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String downloadHandler(Model model, HttpServletRequest request) {
        Cookie cookie[] = request.getCookies();
        Long id = null;
        for (Cookie cookie1 : cookie) {
            if (cookie1.getName().equals("auth")) {
                id = usersService.findUserIdByCookie(cookie1.getValue());
            }
        }
        List<String> projects = usersService.getUsersProjects(id);
        model.addAttribute("projects", projects);
        return "downloadPage";
    }

    @RequestMapping(value = "/download/{projectName}", method = RequestMethod.GET)
    public String projectsHandl(@PathVariable("projectName") String projectName, Model model) {
        String path = System.getProperty("user.dir") + "/uploads/" + projectName;
        File file = new File(path);
        File[] files = file.listFiles();
        List<String> filesName = new ArrayList<>();
        for (File fil : files) {
            filesName.add(fil.getName());
        }
        model.addAttribute("projName", projectName);
        model.addAttribute("files", filesName);
        return "downloadPage";

    }

    @RequestMapping(value = "/download/{projectName}/{fileName}", method = RequestMethod.GET)

    public ResponseEntity<Object> downloadFiles(@PathVariable("projectName") String projectName, @PathVariable("fileName") String fileName) throws FileNotFoundException {

        String directory = System.getProperty("user.dir") + "/uploads/" + projectName + "/" + fileName;
        File file = new File(directory);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object>
                responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(
                MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }
}