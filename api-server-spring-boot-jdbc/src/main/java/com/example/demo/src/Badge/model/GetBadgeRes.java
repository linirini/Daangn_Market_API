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
public class GetBadgeRes {

    @JsonProperty("badge_Id")
    private int badgeId;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("badge_tag_id")
    private int badgeTagId;
    @JsonProperty("badge_status")
    private String badgeStatus;
}
