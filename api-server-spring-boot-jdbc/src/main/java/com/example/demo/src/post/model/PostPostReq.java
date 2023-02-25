package com.example.demo.src.post.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPostReq {

    @JsonProperty("user_id")
    private int userId;
    private String title;
    private Integer price;
    @JsonProperty("price_proposal")
    private String priceProposal;
    private String sharing;
    private String content;
    @JsonProperty("deal_address")
    private String dealAddress;

    @JsonProperty("post_img_urls")
    private List<String> postImgUrls;

}
