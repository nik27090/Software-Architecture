package com.arch.soft.server.servic;

import com.arch.soft.database.model.order.KeyCar;
import com.arch.soft.database.model.order.MasterCall;
import com.arch.soft.database.model.order.Offer;
import com.arch.soft.database.model.person.Master;
import com.arch.soft.database.model.person.Person;
import com.arch.soft.database.repos.KeyCarRepository;
import com.arch.soft.database.repos.MasterCallRepository;
import com.arch.soft.database.repos.OfferRepository;
import com.arch.soft.server.respmodel.KeyCarResponse;
import com.arch.soft.server.respmodel.MasterCallResponse;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class MasterService {

    private final MasterCallRepository masterCallRepository;
    private final KeyCarRepository keyCarRepository;
    private final OfferRepository offerRepository;

    public MasterService(MasterCallRepository masterCallRepository, KeyCarRepository keyCarRepository
            , OfferRepository offerRepository) {
        this.masterCallRepository = masterCallRepository;
        this.keyCarRepository = keyCarRepository;
        this.offerRepository = offerRepository;
    }


    public String closeKeyCar(String idKeyCar, Person person) {
        KeyCar keyCar = keyCarRepository.findById(Long.parseLong(idKeyCar)).get();
        Master master = person.getMaster();
        master.closeKeyCar(keyCar, keyCarRepository);

        return "redirect:/master/main";
    }

    public String closeCall(String idCall, Person person) {
        MasterCall masterCall = masterCallRepository.findById(Long.parseLong(idCall)).get();
        Master master = person.getMaster();
        master.closeCall(masterCall, masterCallRepository);

        return "redirect:/master/main";
    }

    public String createReport(String report, long idCall, Person person) {
        Master master = person.getMaster();
        MasterCall call = masterCallRepository.findById(idCall).get();
        master.createReport(call, masterCallRepository, report);

        return "redirect:/master/main";
    }

    public String createOffer(String offerReq, long idKeyCar, Person person) {
        Master master = person.getMaster();
        KeyCar keyCar = keyCarRepository.findById(idKeyCar).get();
        Offer offer = new Offer();
        offer.setConclusion(offerReq);
        offer.setKeyCar(keyCar);
        master.createOffer(keyCar, keyCarRepository, offer, offerRepository);

        return "redirect:/master/main";
    }

    public String viewOrders(Person person, Map<String, Object> model) {
        List<MasterCall> masterCalls = person.getMaster().getMasterCalls();
        Iterable<MasterCallResponse> masterCallResponses = convertMasterCallsForOld(masterCalls);

        List<KeyCar> keyCars = person.getMaster().getKeyCars();
        Iterable<KeyCarResponse> keyCarResponses = convertKeyCarForOld(keyCars);

        model.put("keyCar", keyCarResponses);
        model.put("masterCalls", masterCallResponses);

        return "ordersMaster";
    }

    public String dontTakeOrderCall(long idCall, Person person) {
        MasterCall masterCall = masterCallRepository.findById(idCall).get();
        Master master = person.getMaster();
        master.dontTakeCall(masterCall, masterCallRepository);

        return "redirect:/master/newOrders";
    }

    public String dontTakeOrderKeyCar(long idKeyCar, Person person) {
        KeyCar keyCar = keyCarRepository.findById(idKeyCar).get();
        Master master = person.getMaster();
        master.dontTakeKeyCar(keyCar, keyCarRepository);

        return "redirect:/master/newOrders";
    }


    public String takeOrderCall(long idCall, Person person) {
        MasterCall masterCall = masterCallRepository.findById(idCall).get();
        Master master = person.getMaster();
        master.takeOrderCall(masterCall, masterCallRepository);

        return "redirect:/master/main";
    }

    public String takeOrderKeyCar(long idKeyCar, Person person) {
        KeyCar keyCar = keyCarRepository.findById(idKeyCar).get();
        Master master = person.getMaster();
        master.takeOrderKeyCar(keyCar, keyCarRepository);

        return "redirect:/master/main";
    }

    public String viewNewOrders(Person person, Map<String, Object> model) {
        List<MasterCall> masterCalls = person.getMaster().getMasterCalls();
        Iterable<MasterCallResponse> masterCallResponses = convertMasterCallsForNew(masterCalls);

        List<KeyCar> keyCars = person.getMaster().getKeyCars();
        Iterable<KeyCarResponse> keyCarResponses = convertKeyCarForNew(keyCars);

        model.put("masterCalls", masterCallResponses);
        model.put("keyCar", keyCarResponses);

        return "newOrdersMaster";
    }

    private Iterable<KeyCarResponse> convertKeyCarForNew(List<KeyCar> keyCars) {
        List<KeyCarResponse> keyCarResponses = new LinkedList<>();

        for (KeyCar keyCar : keyCars) {
            if (keyCar.isMasterAccept()) {
                continue;
            }
            fillResponseForKeyCar(keyCarResponses, keyCar);
        }
        return keyCarResponses;
    }

    private Iterable<KeyCarResponse> convertKeyCarForOld(List<KeyCar> keyCars) {
        List<KeyCarResponse> keyCarResponses = new LinkedList<>();

        for (KeyCar keyCar : keyCars) {
            if (!keyCar.isMasterAccept()) {
                continue;
            }
            fillResponseForKeyCar(keyCarResponses, keyCar);
        }
        return keyCarResponses;
    }

    private Iterable<MasterCallResponse> convertMasterCallsForNew(List<MasterCall> masterCalls) {
        List<MasterCallResponse> masterCallResponses = new LinkedList<>();

        for (MasterCall call : masterCalls) {
            if (call.isMasterAccept()) {
                continue;
            }
            fillResponseForCall(masterCallResponses, call);
        }

        return masterCallResponses;
    }

    private Iterable<MasterCallResponse> convertMasterCallsForOld(List<MasterCall> masterCalls) {
        List<MasterCallResponse> masterCallResponses = new LinkedList<>();

        for (MasterCall call : masterCalls) {
            if (!call.isMasterAccept()) {
                continue;
            }
            fillResponseForCall(masterCallResponses, call);
        }

        return masterCallResponses;
    }

    private void fillResponseForCall(List<MasterCallResponse> masterCallResponses, MasterCall call) {
        MasterCallResponse callResponse = new MasterCallResponse();
        callResponse.setClient(call.getClient().getPerson().getFullName());
        callResponse.setDesc(call.getAutoInfo());
        callResponse.setId(call.getId());
        callResponse.setPrice(call.getPrice());
        callResponse.setStatus("В ожидании ответа");
        if (call.getConclusion() == null) {
            callResponse.setConclusion("В процессе");
        } else {
            callResponse.setConclusion(call.getConclusion());
        }
        masterCallResponses.add(callResponse);
    }

    private void fillResponseForKeyCar(List<KeyCarResponse> keyCarResponses, KeyCar keyCar) {
        KeyCarResponse keyCarResponse = new KeyCarResponse();
        keyCarResponse.setClient(keyCar.getClient().getPerson().getFullName());
        keyCarResponse.setDesc(keyCar.getDesc());
        keyCarResponse.setId(keyCar.getId());
        keyCarResponse.setPrice(keyCar.getPrice());
        keyCarResponse.setStatus("В ожидании ответа");

        keyCarResponses.add(keyCarResponse);
    }
}
