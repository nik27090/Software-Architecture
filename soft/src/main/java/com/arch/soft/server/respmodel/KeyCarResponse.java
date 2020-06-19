package com.arch.soft.server.respmodel;

import lombok.Data;

import java.util.List;

@Data
public class KeyCarResponse {
    private Long id;
    private String client;
    private String master;
    private String desc;
    private String status;
    private List<String> offers;
    private int price;
}
