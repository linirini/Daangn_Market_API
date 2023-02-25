package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Post {

    private int postId;
    private int userId;
    private String title;
    private int price;
    private String priceProposal;
    private String salesStatus;
    private String sharing;
    private String content;
    private String pullUp;
    private String dealAddress;
    private String status;
    private String createdAt;
    private String updatedAt;

}
