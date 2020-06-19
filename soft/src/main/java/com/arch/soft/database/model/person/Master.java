package com.arch.soft.database.model.person;

import com.arch.soft.database.model.BaseEntity;
import com.arch.soft.database.model.order.KeyCar;
import com.arch.soft.database.model.order.MasterCall;
import com.arch.soft.database.model.order.Offer;
import com.arch.soft.database.repos.KeyCarRepository;
import com.arch.soft.database.repos.MasterCallRepository;
import com.arch.soft.database.repos.OfferRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "masters")
@EqualsAndHashCode(callSuper = true)
public class Master extends BaseEntity {
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "rating", nullable = false)
    private double rating;

    @Column(name = "countOrders", nullable = false)
    private int countOrders;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "master", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<KeyCar> keyCars;

    @OneToMany(mappedBy = "master", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<MasterCall> masterCalls;

    public void createReport(MasterCall call, MasterCallRepository masterCallRepository, String report) {
        call.setConclusion(report);
        masterCallRepository.save(call);
    }

    public void createOffer(KeyCar keyCar, KeyCarRepository keyCarRepository, Offer offer, OfferRepository offerRepository) {
        keyCar.addOffer(offer);
        offerRepository.save(offer);
        keyCarRepository.save(keyCar);
    }

    public void dontTakeCall(MasterCall masterCall, MasterCallRepository masterCallRepository) {
        masterCall.setMaster(null);
        masterCall.setMasterAccept(false);
        masterCallRepository.save(masterCall);
    }

    public void takeOrderCall(MasterCall masterCall, MasterCallRepository masterCallRepository) {
        masterCall.setMasterAccept(true);
        masterCallRepository.save(masterCall);
    }

    public void takeOrderKeyCar(KeyCar keyCar, KeyCarRepository keyCarRepository) {
        keyCar.setMasterAccept(true);
        keyCarRepository.save(keyCar);
    }

    public void dontTakeKeyCar(KeyCar keyCar, KeyCarRepository keyCarRepository) {
        keyCar.setMaster(null);
        keyCar.setMasterAccept(false);
        keyCarRepository.save(keyCar);
    }

    public void closeCall(MasterCall masterCall, MasterCallRepository masterCallRepository) {
        masterCall.setActive(false);
        masterCallRepository.saveAndFlush(masterCall);
    }

    public void closeKeyCar(KeyCar keyCar, KeyCarRepository keyCarRepository) {
        keyCar.setActive(false);
        keyCarRepository.saveAndFlush(keyCar);
    }
}
