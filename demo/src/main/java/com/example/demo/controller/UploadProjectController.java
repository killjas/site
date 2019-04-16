package com.example.demo.controller;

import org.springframework.stereotype.Controller;

@Controller
public class UploadProjectController {
//    @Autowired
//    UsersService usersService;
//
//
//    @RequestMapping(value = "/uploadProject", method = RequestMethod.GET)
//    public String upFileHandler() {
//        return "uploadFilePage";
//    }
//
//    @RequestMapping(value = "/uploadProject", method = RequestMethod.POST)
//    public String upload(HttpServletRequest request, Model model, @RequestParam("files") MultipartFile[] files, @RequestParam("projectName") String projectName) {
//        Cookie cookie[] = request.getCookies();
//        Long id = null;
//        for (Cookie cookie1 : cookie) {
//            if (cookie1.getName().equals("auth")) {
//                id = usersService.findUserIdByCookie(cookie1.getValue());
//            }
//        }
//        StringBuilder fileNames = new StringBuilder();
//        String uploadDirectory = System.getProperty("user.dir") + "/uploads" + "/" + projectName;
//        File path = new File(uploadDirectory);
//        path.mkdirs();
//        for (MultipartFile file : files) {
//            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
//            fileNames.append(file.getOriginalFilename() + " ");
//            try {
//                Files.write(fileNameAndPath, file.getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (id != null) {
//            usersService.addProject(id, projectName);
//        }
//        model.addAttribute("msg", "Successfully uploaded files ");
//        return "uploadResult";
//    }
}
