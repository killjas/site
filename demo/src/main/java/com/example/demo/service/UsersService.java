package com.example.demo.service;

import com.example.demo.form.SignInForm;
import com.example.demo.form.SignUpForm;
import com.example.demo.model.User;

import javax.servlet.http.Cookie;
import java.util.List;

public interface UsersService {
    List<User> findAll();
    String signIn(SignInForm signInForm);
    void signUp(SignUpForm signUpForm);
    void delete (String cookie);
    boolean isExistByCookie(String value);

    void addProject(Long userId, String projectName);

    Long findUserIdByCookie(String value);

    List<String> getUsersProjects(Long userId);
}
