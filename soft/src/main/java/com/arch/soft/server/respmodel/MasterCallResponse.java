package com.arch.soft.server.respmodel;

import lombok.Data;

@Data
public class MasterCallResponse {
    private Long id;
    private String client;
    private String master;
    private String desc;
    private String status;
    private String conclusion;
    private int price;
}
