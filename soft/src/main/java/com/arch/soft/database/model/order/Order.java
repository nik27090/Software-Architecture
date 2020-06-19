package com.arch.soft.database.model.order;

import com.arch.soft.database.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity {
    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "master_accept", nullable = false)
    private boolean masterAccept;

    @Column(name = "active", nullable = false)
    private boolean active;
}
