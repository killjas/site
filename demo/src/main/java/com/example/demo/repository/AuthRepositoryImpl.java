package com.example.demo.repository;

import com.example.demo.model.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthRepositoryImpl implements AuthRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //language=SQL
    private static final String SQL_DELETE_COOKIE =
            "delete from auth WHERE cookie_value = ?";
    //language=SQL
    private static final String SQL_INSERT =
            "insert into auth(user_id, cookie_value) values (?, ?)";

    //language=SQL
    private static final String SQL_SELECT_BY_COOKIE_VALUE =
            "select * from auth where cookie_value = ?";

    private RowMapper<Auth> authRowMapper = (rs, rowNum) -> Auth.builder()
            .id(rs.getLong("user_id"))
            .cookieValue(rs.getString("cookie_value"))
            .build();
    @Override
    public void save(Auth auth) {
        jdbcTemplate.update(SQL_INSERT, auth.getUser().getId(), auth.getCookieValue());
    }

    @Override
    public Auth findByCookieValue(String value) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_COOKIE_VALUE, authRowMapper, value);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(String cookie) {
        jdbcTemplate.update(SQL_DELETE_COOKIE, cookie);
    }
}
