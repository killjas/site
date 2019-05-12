package com.example.demo.service;

import com.example.demo.form.SignInForm;
import com.example.demo.form.SignUpForm;
import com.example.demo.model.Auth;
import com.example.demo.model.User;
import com.example.demo.repository.AuthRepository;
import com.example.demo.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UsersServiceImpl implements UsersService {
    @Autowired
    AuthRepository authRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return null;
    }

    @Override
    public String signIn(SignInForm signInForm) {
        Optional<User> userOptional = usersRepository.findByName(signInForm.getLogin());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!passwordEncoder.matches(signInForm.getPassword(), user.getHashPassword())) {
                throw new IllegalArgumentException("Wrong password or email");
            }
        } else throw new IllegalArgumentException("Wrong password or email");


        if (user != null && passwordEncoder.matches(signInForm.getPassword(), user.getHashPassword())) {
            String cookieValue = UUID.randomUUID().toString();

            Auth auth = Auth.builder()
                    .user(user)
                    .cookieValue(cookieValue)
                    .build();

            authRepository.save(auth);

            return cookieValue;
        }
        return null;
    }

    @Override
    public void signUp(SignUpForm form) {
        User user = User.builder()
                .name(form.getLogin())
                .hashPassword(passwordEncoder.encode(form.getPassword()))
                .role(form.getRole())
                .build();

        usersRepository.save(user);
    }

    @Override
    public void delete(String cookie) {
        authRepository.delete(cookie);
    }

    @Override
    public boolean isExistByCookie(String value) {
        if (authRepository.findByCookieValue(value) != null) {
            return true;
        }
        return false;
    }

    @Override
    public void addProject(Long userId, String projectName) {
        usersRepository.newProject(userId, projectName);
    }

    @Override
    public User findUserByCookie(String value){
        return usersRepository.findByCookie(value).get();
    }
    @Override
    public List<String> getUsersProjects(Long userId){
        return usersRepository.getProjects(userId);
    }
}
