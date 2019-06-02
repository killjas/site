package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Controller
public class TaskController {
    List<String> stringList = new ArrayList<>();

    @GetMapping("/task")
    public String index(Model model, HttpServletRequest request) throws IOException {
        Cookie cookie[] = request.getCookies();
        for (Cookie cook : cookie) {
            if (cook.getName().equals("auth")) ;
            model.addAttribute("loged", "loged");
        }
        String path = System.getProperty("user.dir") + "\\task";

        File file = new File(path);
        File[] files = file.listFiles();
        ArrayList<String> fileNames = new ArrayList<>();
        for (File fil : files) {
            fileNames.add(fil.getName());
        }
        Random random = new Random();
        String name = fileNames.get(random.nextInt(files.length));
        FileInputStream fileInputStream = new FileInputStream(path + "\\" + name);
        Scanner scanner = new Scanner(fileInputStream);
        ArrayList<String> fileStr = new ArrayList<>();
        int i;
        int count = 0;
        while (scanner.hasNextLine()) {
            String string1 = "";
            String string = scanner.nextLine();
            string.replace("<", "&lt;");
            string.replace(" ", "&nbsp;");
            string.replace("\"", "&quot;");
            string.replace(">", "&gt;");
            if (random.nextInt(10) == 1) {
                String[] str = string.split(" ");
                i = random.nextInt(str.length);
                stringList.add(str[i]);
                str[i] = "<p><input name = \"task[" + count + "]\"></p>";
                count++;
                for (String string2 : str
                ) {
                    string1 += string2 + " ";
                }
                fileStr.add(string1);
            } else {
                fileStr.add(string);
            }
        }
        fileInputStream.close();
        model.addAttribute("files", fileStr);
        return "task";
    }

    @PostMapping("/task")
    public String checktask(HttpServletRequest request) {
        List<String> strings = (List<String>) request.getAttribute("task");
        for (String s : strings
        ) {
            System.out.println(s);
        }
        return "task";
    }

    @GetMapping("/addTask")
    public String addTaskPage() {
        return "uploadTask";
    }

    @PostMapping("/addTask")
    public String addTask(@RequestParam("files") MultipartFile[] files) {
        StringBuilder fileNames = new StringBuilder();
        String uploadDirectory = System.getProperty("user.dir") + "/task";
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
        return "redirect:/task";
    }
}
