package com.example.demo.service;

import com.example.demo.model.Project;
import com.example.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAllProjects();
    }

    @Override
    public List<Project> findById() {
        return projectRepository.findProjectById();
    }
}
