package com.arch.soft.server.servic;

import com.arch.soft.database.model.Role;
import com.arch.soft.database.model.order.KeyCar;
import com.arch.soft.database.model.order.MasterCall;
import com.arch.soft.database.model.person.Admin;
import com.arch.soft.database.model.person.Client;
import com.arch.soft.database.model.person.Master;
import com.arch.soft.database.model.person.Person;
import com.arch.soft.database.repos.*;
import com.arch.soft.server.dto.ClientRegistrationDto;
import com.arch.soft.server.dto.MasterCallDto;
import com.arch.soft.server.respmodel.KeyCarResponse;
import com.arch.soft.server.respmodel.MasterCallResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {

    private final MasterCallRepository masterCallRepository;
    private final MasterRepository masterRepository;
    private final AdminRepository adminRepository;
    private final PersonRepository personRepository;
    private final ClientRepository clientRepository;
    private final KeyCarRepository keyCarRepository;

    public ClientService(MasterCallRepository masterCallRepository, MasterRepository masterRepository,
                         AdminRepository adminRepository, ClientRepository clientRepository,
                         PersonRepository personRepository, KeyCarRepository keyCarRepository) {
        this.masterCallRepository = masterCallRepository;
        this.masterRepository = masterRepository;
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.personRepository = personRepository;
        this.keyCarRepository = keyCarRepository;
    }

    public Map<String, Object> regClient(ClientRegistrationDto requestClient, Map<String, Object> model) {
        Person personFromDB = personRepository.findByUsername(requestClient.getUsername());

        if (personFromDB != null) {
            model.put("message", "Пользователь с таким username уже существует!");
            return model;
        }

        Client client = new Client();
        client.setCardNumber(requestClient.getCardNumber());

        List<Role> roles = new ArrayList<>();
        roles.add(Role.USER);

        Person person = new Person();
        person.setActive(true);
        person.setClient(client);
        person.setEmail(requestClient.getEmail());
        person.setFullName(requestClient.getFullName());
        person.setPassword(requestClient.getPassword());
        person.setPhoneNumber(requestClient.getPhoneNumber());
        person.setRoles(roles);
        person.setUsername(requestClient.getUsername());

        client.setPerson(person);

        clientRepository.save(client);
        personRepository.save(person);
        return model;
    }

    public Map<String, Object> createKeyCar(MasterCallDto requestMasterCall, Person person, Map<String, Object> model) {
        Client client = person.getClient();

        Long id = adminRepository.findMaxId();
        //todo random
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isEmpty()) {
            model.put("message", "Нет админов");
            return model;
        }
        client.createKeyCar(requestMasterCall.getDesc(), admin.get(), keyCarRepository);

        return model;
    }


    public Map<String, Object> createMasterCall(MasterCallDto requestMasterCall, Person person, Map<String, Object> model) {
        Client client = person.getClient();

        if (client == null) {
            model.put("message", "Вы зашли не под клиентским аккаунтом");
            return model;
        }

        Long id = adminRepository.findMaxId();
        //todo random
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isEmpty()) {
            model.put("message", "Нет админов");
            return model;
        }
        client.createMasterCall(requestMasterCall.getDesc(), admin.get(), masterCallRepository);

        return model;
    }

    public int getStarsForCall(Person person, int star, long idCall) {
        Master master = masterCallRepository.findById(idCall).get().getMaster();
        person.getClient().addStar(star, master, masterRepository);

        return star;
    }

    public int getStarsForKeyCar(Person person, int star, long idCall) {
        Master master = keyCarRepository.findById(idCall).get().getMaster();
        person.getClient().addStar(star, master, masterRepository);

        return star;
    }

    public String viewStars(String idCall, Map<String, Object> model) {
        model.put("idCall", idCall);

        return "getStarUser";
    }

    public Map<String, Object> viewOrders(Person person, Map<String, Object> model) {
        List<MasterCall> calls = person.getClient().getMasterCalls();
        Iterable<MasterCallResponse> masterCallResponses = convertMasterCall(calls);

        List<KeyCar> keyCars = person.getClient().getKeyCars();
        Iterable<KeyCarResponse> keyCarResponses = convertKeyCat(keyCars);

        model.put("keyCar", keyCarResponses);
        model.put("masterCalls", masterCallResponses);

        return model;
    }

    private Iterable<KeyCarResponse> convertKeyCat(List<KeyCar> keyCars) {
        List<KeyCarResponse> keyCarResponses = new LinkedList<>();

        for (KeyCar key : keyCars) {
            KeyCarResponse keyCarResponse = new KeyCarResponse();
            keyCarResponse.setClient(key.getClient().getPerson().getFullName());
            keyCarResponse.setDesc(key.getDesc());
            keyCarResponse.setId(key.getId());
            keyCarResponse.setPrice(key.getPrice());
            Master master = key.getMaster();
            if (master == null) {
                keyCarResponse.setStatus("Назначение мастера");
            } else if (!key.getOffers().isEmpty()) {
                keyCarResponse.setStatus("Закрыто");
            } else if (key.isMasterAccept()) {
                keyCarResponse.setStatus("В Процессе");
                keyCarResponse.setMaster(master.getPerson().getFullName());
            } else {
                keyCarResponse.setStatus("Ожидаем ответа мастера");
                keyCarResponse.setMaster(master.getPerson().getFullName());
            }
            keyCarResponses.add(keyCarResponse);
        }

        return keyCarResponses;
    }

    private Iterable<MasterCallResponse> convertMasterCall(List<MasterCall> masterCalls) {
        List<MasterCallResponse> masterCallResponses = new LinkedList<>();

        for (MasterCall call : masterCalls) {
            MasterCallResponse callResponse = new MasterCallResponse();
            callResponse.setClient(call.getClient().getPerson().getFullName());
            callResponse.setDesc(call.getAutoInfo());
            callResponse.setId(call.getId());
            callResponse.setPrice(call.getPrice());
            Master master = call.getMaster();
            if (master == null) {
                callResponse.setStatus("Назначение мастера");
            } else if (call.getConclusion() != null) {
                callResponse.setConclusion(call.getConclusion());
                callResponse.setStatus("Закрыто");
            } else if (call.isMasterAccept()) {
                callResponse.setStatus("В Процессе");
                callResponse.setMaster(master.getPerson().getFullName());
            } else {
                callResponse.setStatus("Ожидаем ответа мастера");
                callResponse.setMaster(master.getPerson().getFullName());
            }
            if (call.getConclusion() == null) {
                callResponse.setConclusion("В процессе");
            } else {
                callResponse.setConclusion(call.getConclusion());
            }
            masterCallResponses.add(callResponse);
        }

        return masterCallResponses;
    }


}

