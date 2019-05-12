package com.example.demo.repository;

import com.example.demo.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectRepositoryImpl implements ProjectRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    //language=SQL
    private static final String SQL_FIND_ALL_PROJECTS =
            "select * from project left join person_project pp on project.id = pp.project_id where pp.person_id=?;";

    private RowMapper<Project> projectRowMapper = (resultSet, i) -> Project.builder()
            .id(resultSet.getLong("id"))
            .userId(resultSet.getLong("person_id"))
            .name(resultSet.getString("project_name"))
            .fullInfo(resultSet.getString("project_full_info"))
            .build();

    @Override
    public void save(Long userId, String projectName, String projectInfo, String path) {

    }

    @Override
    public List findAllProjects(long personId) {
        return jdbcTemplate.query(SQL_FIND_ALL_PROJECTS, projectRowMapper);
    }

    @Override
    public List findProjectById() {
        return null;
    }
}
