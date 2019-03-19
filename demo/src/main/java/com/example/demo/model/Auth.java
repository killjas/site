package com.example.demo.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Auth {
    private Long id;
    private String cookieValue;
    private User user;
}