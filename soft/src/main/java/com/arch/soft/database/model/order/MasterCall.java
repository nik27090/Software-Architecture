package com.arch.soft.database.model.order;

import com.arch.soft.database.model.person.Admin;
import com.arch.soft.database.model.person.Client;
import com.arch.soft.database.model.person.Master;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "master_calls")
@EqualsAndHashCode(callSuper = true)
public class MasterCall extends Order {
    @Column(name = "auto_info", nullable = false)
    private String autoInfo;

    @Column(name = "conclusion")
    private String conclusion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id")
    private Master master;
}
