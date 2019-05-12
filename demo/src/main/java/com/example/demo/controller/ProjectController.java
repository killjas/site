package com.example.demo.controller;

import com.example.demo.model.Project;
import com.example.demo.service.ProjectService;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    UsersService us;
    @GetMapping("/projects")
    public String projects(HttpServletRequest req){
        Cookie cookie=null;
        Cookie[] cookies = req.getCookies();
        for (Cookie value : cookies) {
            if (value.getName().equals("auth")) {
                cookie = value;
            }
        }
        List<Project> projects= projectService.findAll(us.findUserByCookie(cookie.getValue()).getId());
        req.setAttribute("projects", projects);
        return "projects";
    }

    @PostMapping("/projects")
    public String startDockerProj(HttpServletRequest request, @RequestParam("projectName") String project) throws IOException {
        System.out.println(project);
        String dockerComposeData="version: '3'\n" +
                "services:\n" +
                "  nginx:\n" +
                "   container_name: some-nginx\n" +
                "   image: nginx:1.13\n" +
                "   restart: always\n" +
                "   ports:\n" +
                "   - 80:80\n" +
                "   - 443:443\n" +
                "   volumes:\n" +
                "   - ./nginx/conf.d:/etc/nginx/conf.d\n" +
                "   depends_on:\n" +
                "   - app\n" +
                "\n" +
                "  app:\n" +
                "    restart: always\n" +
                "    build: ./"+project+"\n" +
                "    working_dir: /"+project+"\n" +
                "    volumes:\n" +
                "      - ./"+project+":/"+project+"\n" +
                "    expose:\n" +
                "      - \"8080\"\n" +
                "    command: mvn clean spring-boot:run\n";
        String dockerDoc="FROM maven:3.5-jdk-8\n";
        File dockerfile=new File("C:\\Users\\oleji\\Desktop\\site\\demo\\uploads\\"+project+"\\Dockerfile");
        System.out.println(dockerfile.getAbsolutePath());
        FileWriter d=new FileWriter(dockerfile);
        d.write(dockerDoc);
        d.flush();
        d.close();
        File f=new File("C:\\Users\\oleji\\Desktop\\site\\demo\\uploads\\docker-compose.yaml");
        FileWriter fw=new FileWriter(f);
        fw.write(dockerComposeData);
        fw.flush();
        fw.close();
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "cd C:\\Users\\oleji\\Desktop\\site\\demo\\uploads && docker-compose up --build -d");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            }
            System.out.println(line);
        }
        return "redirect:"+request.getScheme()+"://localhost:80";
    }
}
