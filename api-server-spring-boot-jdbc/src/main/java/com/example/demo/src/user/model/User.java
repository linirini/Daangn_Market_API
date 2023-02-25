package com.example.demo.src.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("nick_name")
    private String nickName;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("email_address")
    private String emailAddress;
    @JsonProperty("manner_temp")
    private double mannerTemp;
    @JsonProperty("profile_photo")
    private String profilePhoto;
    @JsonProperty("retransaction_hope_rate")
    private double retransactionHopeRate;
    @JsonProperty("account_status")
    private String accountStatus;
    @JsonProperty("status")
    private String status;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;

}
