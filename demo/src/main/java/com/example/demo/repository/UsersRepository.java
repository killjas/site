package com.example.demo.repository;

import com.example.demo.model.User;

import java.util.Optional;

public interface UsersRepository {
    public Optional<User> findByName(String username);

    public Optional<User> findByCookie(String cookie);



    void save(User user);
}
