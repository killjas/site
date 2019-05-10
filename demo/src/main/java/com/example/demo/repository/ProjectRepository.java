package com.example.demo.repository;

import java.util.List;

public interface ProjectRepository {
    void save(Long userId, String projectName, String projectInfo, String path);
    List findAllProjects();
    List findProjectById();
}
