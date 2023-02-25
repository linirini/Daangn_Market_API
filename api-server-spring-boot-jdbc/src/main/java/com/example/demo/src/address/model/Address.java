package com.example.demo.src.address.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @JsonProperty("address_id")
    private int addressId;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("address")
    private String address;
    @JsonProperty("latitude")
    private double latitude;
    @JsonProperty("longitude")
    private double longitude;
    @JsonProperty("confirm_status")
    private String confirmStatus;
    @JsonProperty("address_status")
    private int addressStatus;
    @JsonProperty("status")
    private String status;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;

}
