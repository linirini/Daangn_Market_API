package com.example.demo.src.Badge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostBadgeReq {

    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("badge_tag_id")
    private Integer badgeTagId;
}
