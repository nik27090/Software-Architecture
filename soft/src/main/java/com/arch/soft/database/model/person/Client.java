package com.arch.soft.database.model.person;

import com.arch.soft.database.model.BaseEntity;
import com.arch.soft.database.model.order.KeyCar;
import com.arch.soft.database.model.order.MasterCall;
import com.arch.soft.database.repos.KeyCarRepository;
import com.arch.soft.database.repos.MasterCallRepository;
import com.arch.soft.database.repos.MasterRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "clients")
@EqualsAndHashCode(callSuper = true)
public class Client extends BaseEntity {
    @Column(name = "card_number")
    private int cardNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<MasterCall> masterCalls;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<KeyCar> keyCars;

    public void createMasterCall(String desc, Admin admin, MasterCallRepository masterCallRepository) {
        MasterCall call = new MasterCall();
        call.setAutoInfo(desc);
        call.setClient(this);
        call.setAdmin(admin);
        call.setPrice(2500);
        call.setMasterAccept(false);
        call.setActive(true);
        //masterCalls.add(call);

        masterCallRepository.save(call);
    }

    public void createKeyCar(String desc, Admin admin, KeyCarRepository keyCarRepository) {
        KeyCar keyCar = new KeyCar();
        keyCar.setDesc(desc);
        keyCar.setAdmin(admin);
        keyCar.setClient(this);
        keyCar.setPrice(5000);
        keyCar.setMasterAccept(false);
        keyCar.setActive(true);

        keyCarRepository.save(keyCar);
    }

    public void addStar(int star, Master master, MasterRepository masterRepository) {
        double rating = master.getRating();
        int countOrders = master.getCountOrders();

        if (rating == 0.0) {
            rating = star;
            master.setRating(rating);
            master.setCountOrders(1);
        } else {
            countOrders++;
            rating = (rating + star) / countOrders;
            master.setRating(rating);
            master.setCountOrders(countOrders);
        }
        masterRepository.save(master);
    }
}
