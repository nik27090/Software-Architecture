package com.arch.soft.server.dto;

import lombok.Data;

@Data
public class MasterRegistrationDto {
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String desc;
    private String countOrder;
}
