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
//    private static final String SQL_SELECT_BY_COOKIE =
//            "select * from person.*,  "
//
    //language=SQL
    private static final String SQL_INSERT =
            "insert into person(login, password) values (?, ?);";

    //language=SQL
    private static final String SQL_INSERT_ROLE = "insert into role_user(user_id, role) values (?, ?)";

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
        return Optional.empty();
    }

    @Override
    public void save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(SQL_INSERT, new String[] {"id"});
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getHashPassword());
                    return ps;
                }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
    }
}
