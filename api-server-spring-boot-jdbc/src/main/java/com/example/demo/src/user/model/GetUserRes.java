package com.example.demo.src.user.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserRes {

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
    @JsonProperty("retransaction_hope_rate")
    private double retransactionHopeRate;
    @JsonProperty("profile_photo")
    private String profilePhoto;
    @JsonProperty("account_status")
    private String accountStatus;

    /*private int userId;
    private String userName;
    private String ID;
    private String email;
    private String password;*/
}
