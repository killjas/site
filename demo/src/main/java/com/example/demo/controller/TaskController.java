package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

@Controller
public class TaskController {
    @GetMapping("/task")
    public String index(Model model) throws IOException {

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
        while (scanner.hasNextLine()) {
            String string = scanner.nextLine();
            fileStr.add(string);
        }
        model.addAttribute("files", fileStr);
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
