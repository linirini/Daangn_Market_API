package com.example.demo.src.interest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Interest {

    private int interestId;
    private int userId;
    private int postId;
    private String interestStatus;
    private String status;
    private String createdAt;
    private String updatedAt;

}
