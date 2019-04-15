package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    PasswordEncoder passwordEncoder;
    //language=SQL
    private static final String SQL_SELECT_BY_EMAIL =
            "select * from person where login = ?;";

    //language=SQL
    private static final String SQL_SELECT_BY_COOKIE =
            "select * from person right join auth a on person.id = a.user_id where cookie_value = ?";

    //language=SQL
    private static final String SQL_INSERT =
            "insert into person(login, password) values (?, ?);";

    //language=SQL
    private static final String SQL_INSERT_ROLE = "insert into role_user(user_id, role) values (?, ?)";
    //language=SQL
    private static final String SQL_ADD_NEW_PROJECT = "insert into person_project(person_id, project_name) values (?,?)";
    //language=SQL
    private static final String SQL_SELECT_USERS_PROJECTS = "select project_name from person_project where person_id=?";

    private RowMapper<User> userRowMapper = ((resultSet, i) -> User.builder()
            .id(resultSet.getLong("id"))
            .name(resultSet.getString("login"))
            .hashPassword(resultSet.getString("password"))
            .build());

    @Override
    public Optional<User> findByName(String username) {
        return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, userRowMapper, username));
    }

    @Override
    public Optional<User> findByCookie(String cookie) {
        return Optional.of(jdbcTemplate.queryForObject(SQL_SELECT_BY_COOKIE, userRowMapper, cookie));
    }

    @Override
    public void save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT, new String[]{"id"});
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getHashPassword());
                    return ps;
                }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        jdbcTemplate.update(SQL_INSERT_ROLE, user.getId(), user.getRole());
    }

    @Override
    public void newProject(Long userId, String projectName) {
        jdbcTemplate.update(SQL_ADD_NEW_PROJECT, userId, projectName);
    }

    @Override
    public List<String> getProjects(Long userId) {
        return jdbcTemplate.queryForList(SQL_SELECT_USERS_PROJECTS,String.class,userId);
    }
}
