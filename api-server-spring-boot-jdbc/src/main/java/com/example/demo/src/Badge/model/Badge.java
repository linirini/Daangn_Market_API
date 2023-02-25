package com.example.demo.src.Badge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Badge {

    private int badgeId;
    private int userId;
    private int badgeTagId;
    private String badgeStatus;
    private String status;
    private String createdAt;
    private String updatedAt;

}
