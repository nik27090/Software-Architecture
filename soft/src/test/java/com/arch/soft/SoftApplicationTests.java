package com.arch.soft;

import com.arch.soft.database.model.order.KeyCar;
import com.arch.soft.database.model.order.MasterCall;
import com.arch.soft.database.model.person.Admin;
import com.arch.soft.database.model.person.Client;
import com.arch.soft.database.model.person.Master;
import com.arch.soft.database.model.person.Person;
import com.arch.soft.database.repos.PersonRepository;
import com.arch.soft.server.dto.ClientRegistrationDto;
import com.arch.soft.server.dto.MasterCallDto;
import com.arch.soft.server.dto.MasterRegistrationDto;
import com.arch.soft.server.servic.AdminService;
import com.arch.soft.server.servic.ClientService;
import com.arch.soft.server.servic.MasterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class SoftApplicationTests {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private MasterService masterService;

    @Test
    void masterCall() {
        //admin
        Admin admin = personRepository.findById(1L).get().getAdmin();
        //client
        ClientRegistrationDto registrationDto = new ClientRegistrationDto();
        registrationDto.setCardNumber(0);
        registrationDto.setEmail("client3");
        registrationDto.setFullName("test client");
        registrationDto.setPassword("test");
        registrationDto.setUsername("testClient3");
        registrationDto.setPhoneNumber("200");
        Map<String, Object> modelC = new HashMap<>();
        clientService.regClient(registrationDto, modelC);

        Person personClient = personRepository.findByUsername(registrationDto.getUsername());
        Client client = personClient.getClient();

        //master
        MasterRegistrationDto masterRegistrationDto = new MasterRegistrationDto();
        masterRegistrationDto.setCountOrder("0");
        masterRegistrationDto.setDesc("умеет все");
        masterRegistrationDto.setEmail("master4");
        masterRegistrationDto.setFullName("Master");
        masterRegistrationDto.setPassword("test");
        masterRegistrationDto.setPhoneNumber("300");
        masterRegistrationDto.setUsername("testmaster4");
        Map<String, Object> modelA = new HashMap<>();
        adminService.masterReg(masterRegistrationDto, admin.getPerson(), modelA);

        Person personMaster = personRepository.findByUsername(masterRegistrationDto.getUsername());
        Master master = personMaster.getMaster();

        //creat MasterCall
        MasterCallDto masterCallDto = new MasterCallDto();
        masterCallDto.setDesc("Бэха тройка 2011г завтра в 18:00 на Ленина 4/1");
        Map<String, Object> modelCall = new HashMap<>();
        clientService.createMasterCall(masterCallDto, client.getPerson(), modelCall);

        //select master
        List<MasterCall> masterCalls = client.getMasterCalls();
        MasterCall masterCall = null;
        for (MasterCall call : masterCalls) {
            if (masterCallDto.getDesc().equals(call.getAutoInfo())) {
                masterCall = call;
            }
        }
        assert masterCall != null;
        Map<String, Object> modelSelect = new HashMap<>();
        adminService.selectMasterForCall(masterCall.getId().toString(), admin.getPerson(), master.getId(), modelSelect);

        //accept order
        masterService.takeOrderCall(masterCall.getId(), master.getPerson());

        //create report
        masterService.createReport("all good", masterCall.getId(), master.getPerson());

        //close call
        masterService.closeCall(masterCall.getId().toString(), master.getPerson());

        //get stars
        clientService.getStarsForCall(client.getPerson(), 5, masterCall.getId());

        //clear
        personRepository.deleteById(personClient.getId());
        personRepository.deleteById(personMaster.getId());
    }

    @Test
    void keyCar() {
        //admin
        Admin admin = personRepository.findById(1L).get().getAdmin();
        //client
        ClientRegistrationDto registrationDto = new ClientRegistrationDto();
        registrationDto.setCardNumber(0);
        registrationDto.setEmail("client3");
        registrationDto.setFullName("test client");
        registrationDto.setPassword("test");
        registrationDto.setUsername("testClient3");
        registrationDto.setPhoneNumber("200");
        Map<String, Object> modelC = new HashMap<>();
        clientService.regClient(registrationDto, modelC);

        Person personClient = personRepository.findByUsername(registrationDto.getUsername());
        Client client = personClient.getClient();

        //master
        MasterRegistrationDto masterRegistrationDto = new MasterRegistrationDto();
        masterRegistrationDto.setCountOrder("0");
        masterRegistrationDto.setDesc("умеет все");
        masterRegistrationDto.setEmail("master4");
        masterRegistrationDto.setFullName("Master");
        masterRegistrationDto.setPassword("test");
        masterRegistrationDto.setPhoneNumber("300");
        masterRegistrationDto.setUsername("testmaster4");
        Map<String, Object> modelA = new HashMap<>();
        adminService.masterReg(masterRegistrationDto, admin.getPerson(), modelA);

        Person personMaster = personRepository.findByUsername(masterRegistrationDto.getUsername());
        Master master = personMaster.getMaster();

        //create keyCar
        MasterCallDto keyCarDto = new MasterCallDto();
        keyCarDto.setDesc("supra, 4 ляма, через неделю, ни бита, ни крашена");
        Map<String, Object> modelKey = new HashMap<>();
        clientService.createKeyCar(keyCarDto, client.getPerson(), modelKey);

        //select master
        List<KeyCar> keyCars = client.getKeyCars();
        KeyCar keyCar = null;
        for (KeyCar key : keyCars) {
            if (keyCarDto.getDesc().equals(key.getDesc())) {
                keyCar = key;
            }
        }
        assert keyCar != null;
        Map<String, Object> modelSelect = new HashMap<>();
        adminService.selectMasterForKeyCar(keyCar.getId().toString(), admin.getPerson(), master.getId(), modelSelect);

        //accept order
        masterService.takeOrderKeyCar(keyCar.getId(), master.getPerson());

        //create offer
        masterService.createOffer("сам ищи", keyCar.getId(), master.getPerson());
        masterService.createOffer("у меня друг как раз продает", keyCar.getId(), master.getPerson());

        //close call
        masterService.closeKeyCar(keyCar.getId().toString(), master.getPerson());

        //get stars
        clientService.getStarsForKeyCar(client.getPerson(), 5, keyCar.getId());

        //clear
        personRepository.deleteById(personClient.getId());
        personRepository.deleteById(personMaster.getId());
    }

}
