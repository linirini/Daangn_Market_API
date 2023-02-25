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
public class PostUserReq {

    @JsonProperty("nick_name")
    private String nickName;
    @JsonProperty("phone_number")
    private String phoneNumber;

/*    private String UserName;
    private String id;
    private String email;
    private String password;*/
}
