package com.example.demo.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
public class User {
    private Long id;
    private String name;
    private String hashPassword;
    private String role;
}
