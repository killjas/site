package com.example.demo.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Project {
    private Long id;
    private Long userId;
    private String name;
    private String fullInfo;
}
