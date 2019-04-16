package com.example.demo.repository;

import com.example.demo.model.Auth;

public interface AuthRepository {

    void save(Auth auth);

    Auth findByCookieValue(String value);

    void delete(String cookie);
}
