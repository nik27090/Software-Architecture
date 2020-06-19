package com.arch.soft.database.model.person;

import com.arch.soft.database.model.BaseEntity;
import com.arch.soft.database.model.Role;
import com.arch.soft.database.model.order.KeyCar;
import com.arch.soft.database.model.order.MasterCall;
import com.arch.soft.database.repos.KeyCarRepository;
import com.arch.soft.database.repos.MasterCallRepository;
import com.arch.soft.database.repos.MasterRepository;
import com.arch.soft.database.repos.PersonRepository;
import com.arch.soft.server.dto.MasterRegistrationDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "admins")
@EqualsAndHashCode(callSuper = true)
public class Admin extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
    private List<KeyCar> keyCars;

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY)
    private List<MasterCall> masterCalls;

    public void selectMasterForCall(Master master, MasterCall masterCall, MasterCallRepository masterCallRepository) {
        masterCall.setMaster(master);
        masterCallRepository.save(masterCall);
    }

    public void selectMasterForKeyCar(Master master, KeyCar keyCar, KeyCarRepository keyCarRepository) {
        keyCar.setMaster(master);
        keyCarRepository.save(keyCar);
    }

    public void createMaster(MasterRegistrationDto registrationDto, MasterRepository masterRepository,
                             PersonRepository personRepository) {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.MASTER);

        Person person = new Person();
        person.setUsername(registrationDto.getUsername());
        person.setActive(true);
        person.setEmail(registrationDto.getEmail());
        person.setFullName(registrationDto.getFullName());
        person.setPassword(registrationDto.getPassword());
        person.setPhoneNumber(registrationDto.getPhoneNumber());
        person.setRoles(roles);


        Master master = new Master();
        master.setDescription(registrationDto.getDesc());
        master.setRating(0.0);
        master.setCountOrders(0);
        person.setMaster(master);

        master.setPerson(person);

        masterRepository.save(master);
        personRepository.save(person);
    }
}
