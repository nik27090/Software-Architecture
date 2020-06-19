package com.arch.soft.database.model.order;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created")
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated")
    private Date updated;

    @Column(name = "conclusion")
    private String conclusion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "key_car_id", nullable = false)
    private KeyCar keyCar;
}
