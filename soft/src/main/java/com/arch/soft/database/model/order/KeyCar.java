package com.arch.soft.database.model.order;

import com.arch.soft.database.model.person.Admin;
import com.arch.soft.database.model.person.Client;
import com.arch.soft.database.model.person.Master;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "key_cars")
@EqualsAndHashCode(callSuper = true)
public class KeyCar extends Order {
    @Column(name = "description", nullable = false)
    private String desc;

    @OneToMany(mappedBy = "keyCar", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Offer> offers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id")
    private Master master;

    public void addOffer(Offer offer) {
        offers.add(offer);
    }
}
