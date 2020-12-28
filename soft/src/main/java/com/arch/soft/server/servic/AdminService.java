package com.arch.soft.server.servic;

import com.arch.soft.database.model.order.KeyCar;
import com.arch.soft.database.model.order.MasterCall;
import com.arch.soft.database.model.person.Admin;
import com.arch.soft.database.model.person.Master;
import com.arch.soft.database.model.person.Person;
import com.arch.soft.database.repos.KeyCarRepository;
import com.arch.soft.database.repos.MasterCallRepository;
import com.arch.soft.database.repos.MasterRepository;
import com.arch.soft.database.repos.PersonRepository;
import com.arch.soft.server.dto.MasterRegistrationDto;
import com.arch.soft.server.respmodel.KeyCarResponse;
import com.arch.soft.server.respmodel.MasterCallResponse;
import com.arch.soft.server.respmodel.MasterResponse;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AdminService {
    private final MasterCallRepository masterCallRepository;
    private final MasterRepository masterRepository;
    private final PersonRepository personRepository;
    private final KeyCarRepository keyCarRepository;

    public AdminService(MasterCallRepository masterCallRepository, MasterRepository masterRepository,
                        PersonRepository personRepository, KeyCarRepository keyCarRepository) {
        this.keyCarRepository = keyCarRepository;
        this.masterCallRepository = masterCallRepository;
        this.masterRepository = masterRepository;
        this.personRepository = personRepository;
    }

    public Map<String, Object> masterReg(MasterRegistrationDto registrationDto, Person person, Map<String, Object> model) {
        Admin admin = person.getAdmin();
        admin.createMaster(registrationDto, masterRepository, personRepository);

        model.put("message", "Мастер зарегестрирован");
        return model;
    }

    public Map<String, Object> selectMasterForCall(String idCall, Person person, Long idMaster, Map<String, Object> model) {
        Admin admin = person.getAdmin();
        Optional<Master> master = masterRepository.findById(idMaster);
        Optional<MasterCall> masterCall = masterCallRepository.findById(Long.parseLong(idCall));
        if (master.isEmpty() || masterCall.isEmpty()) {
            model.put("message", "Ошибка в selectMaster");
            return model;
        }
        admin.selectMasterForCall(master.get(), masterCall.get(), masterCallRepository);

        model.put("message", "Мастеру отправленно уведовмление");
        return model;
    }

    public Map<String, Object> viewMastersForCall(String idCall, Map<String, Object> model) {
        Optional<MasterCall> masterCall = masterCallRepository.findById(Long.parseLong(idCall));

        if (masterCall.isEmpty()) {
            model.put("message", "Заяки почему-то не существует");
            return model;
        } else if (masterCall.get().getMaster() != null) {
            model.put("message", "Мастер уже назначен");
            return model;
        }
        List<Master> masters = masterRepository.findAll();
        Iterable<MasterResponse> masterResponses = convertMaster(masters);
        model.put("master", masterResponses);
        model.put("idCall", idCall);

        return model;
    }

    public Map<String, Object> viewMastersForKeyCar(String idKeyCar, Map<String, Object> model) {
        Optional<KeyCar> keyCar = keyCarRepository.findById(Long.parseLong(idKeyCar));

        if (keyCar.isEmpty()) {
            model.put("message", "Заяки почему-то не существует");
            return model;
        } else if (keyCar.get().getMaster() != null) {
            model.put("message", "Мастер уже назначен");
            return model;
        }
        List<Master> masters = masterRepository.findAll();
        Iterable<MasterResponse> masterResponses = convertMaster(masters);
        model.put("master", masterResponses);
        model.put("idKeyCar", idKeyCar);

        return model;
    }

    public Map<String, Object> selectMasterForKeyCar(String idKeyCar, Person person, Long idMaster, Map<String, Object> model) {
        Admin admin = person.getAdmin();
        Optional<Master> master = masterRepository.findById(idMaster);
        Optional<KeyCar> keyCar = keyCarRepository.findById(Long.parseLong(idKeyCar));
        if (master.isEmpty() || keyCar.isEmpty()) {
            model.put("message", "Ошибка в selectMaster");
            return model;
        }
        admin.selectMasterForKeyCar(master.get(), keyCar.get(), keyCarRepository);

        model.put("message", "Мастеру отправленно уведовмление");
        return model;
    }

    public Map<String, Object> fillMainPage(Person person, Map<String, Object> model) {
        List<MasterCall> masterCalls = person.getAdmin().getMasterCalls();
        Iterable<MasterCallResponse> masterCallResponses = convertMasterCall(masterCalls);

        List<KeyCar> keyCars = person.getAdmin().getKeyCars();
        Iterable<KeyCarResponse> keyCarsResponses = convertKeyCar(keyCars);

        model.put("masterCalls", masterCallResponses);
        model.put("keyCar", keyCarsResponses);

        return model;
    }

    private Iterable<KeyCarResponse> convertKeyCar(List<KeyCar> keyCars) {
        List<KeyCarResponse> keyCarResponses = new LinkedList<>();

        for (KeyCar keyCar : keyCars) {
            KeyCarResponse keyCarResponse = new KeyCarResponse();
            keyCarResponse.setClient(keyCar.getClient().getPerson().getFullName());
            keyCarResponse.setDesc(keyCar.getDesc());
            keyCarResponse.setId(keyCar.getId());
            keyCarResponse.setPrice(keyCar.getPrice());
            Master master = keyCar.getMaster();
            if (master == null) {
                keyCarResponse.setStatus("Назначение мастера");
            } else if (!keyCar.getOffers().isEmpty()) {
                keyCarResponse.setStatus("Закрыто");
            } else if (keyCar.isMasterAccept()) {
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
                callResponse.setStatus("Закрыто");
            } else if (call.isMasterAccept()) {
                callResponse.setStatus("В Процессе");
                callResponse.setMaster(master.getPerson().getFullName());
            } else {
                callResponse.setStatus("Ожидаем ответа мастера");
                callResponse.setMaster(master.getPerson().getFullName());
            }
            masterCallResponses.add(callResponse);
        }

        return masterCallResponses;
    }

    private Iterable<MasterResponse> convertMaster(List<Master> masterList) {
        List<MasterResponse> result = new LinkedList<>();

        for (Master master : masterList) {
            MasterResponse masterResponse = new MasterResponse();
            masterResponse.setId(master.getId());
            masterResponse.setDesc(master.getDescription());
            masterResponse.setFullName(master.getPerson().getFullName());
            result.add(masterResponse);
        }
        return result;
    }


}
