package com.example.demo.src.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchUserReq {

    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("nick_name")
    private String nickName;
    @JsonProperty("profile_photo")
    private String profilePhoto;

}
