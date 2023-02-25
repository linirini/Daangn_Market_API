package com.example.demo.src.post.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPostRes {

    @JsonProperty("post_id")
    private int postId;
    @JsonProperty("user_id")
    private int userId;
    private String title;
    private int price;
    @JsonProperty("price_proposal")
    private String priceProposal;
    @JsonProperty("sales_status")
    private String salesStatus;
    private String sharing;
    private String content;
    @JsonProperty("pull_up")
    private String pullUp;
    @JsonProperty("deal_address")
    private String dealAddress;

}
