package com.example.demo.src.post.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPostImageUrlRes {

    @JsonProperty("image_url")
    private String imageUrl;

}
