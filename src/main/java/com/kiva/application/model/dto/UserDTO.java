package com.kiva.application.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO {
    private long id;
    private String fullName;
    private String email;
    private String city;
    private String district;
    private String ward;
    private String address;
    private String phone;
    private String avatar;
    private List<String> roles;
}
