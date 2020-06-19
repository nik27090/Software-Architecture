package com.arch.soft.server.dto;

import lombok.Data;

@Data
public class ClientRegistrationDto {
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String email;
    private int cardNumber;
}
