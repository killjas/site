package com.example.demo.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "hashPassword")
@Builder
public class User {
    private Long id;
    private String name;
    private String hashPassword;
    private String role;
}
