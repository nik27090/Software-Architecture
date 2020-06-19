package com.arch.soft.server.controllers.master;

import com.arch.soft.database.model.person.Person;
import com.arch.soft.server.servic.MasterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/master")
@PreAuthorize("hasAuthority('MASTER')")
public class MasterMainController {

    private final MasterService masterService;

    public MasterMainController(MasterService masterService) {
        this.masterService = masterService;
    }

    @GetMapping("/main")
    public String mainPage(
    ) {
        return "mainMaster";
    }

    @GetMapping("/newOrders")
    public String newOrders(
            @AuthenticationPrincipal Person person,
            Map<String, Object> model) {
        return masterService.viewNewOrders(person, model);
    }

    @GetMapping("/orders")
    public String orders(
            @AuthenticationPrincipal Person person,
            Map<String, Object> model
    ) {
        return masterService.viewOrders(person, model);
    }

    @GetMapping("/masterCall/{{idCall}}/ok")
    public String agreeCall(
            @AuthenticationPrincipal Person person,
            @PathVariable("{idCall}") long idCall) {
        return masterService.takeOrderCall(idCall, person);
    }

    @GetMapping("/masterCall/{{idCall}}/cancel")
    public String disagreeCall(
            @AuthenticationPrincipal Person person,
            @PathVariable("{idCall}") long idCall) {
        return masterService.dontTakeOrderCall(idCall, person);
    }

    @GetMapping("/keyCar/{{idKeyCar}}/ok")
    public String agreeKeyCar(
            @AuthenticationPrincipal Person person,
            @PathVariable("{idKeyCar}") long idKeyCar) {
        return masterService.takeOrderKeyCar(idKeyCar, person);
    }

    @GetMapping("/keyCar/{{idKeyCar}}/cancel")
    public String disagreeKeyCar(
            @AuthenticationPrincipal Person person,
            @PathVariable("{idKeyCar}") long idKeyCar) {
        return masterService.dontTakeOrderKeyCar(idKeyCar, person);
    }

    @GetMapping("/report/{{idCall}}")
    public String createReportCall(
            Map<String, Object> model,
            @PathVariable("{idCall}") String idCall) {

        model.put("idCall", idCall);
        return "createReportMaster";
    }

    @PostMapping("/report/{{idCall}}")
    public String createReportCall(
            @AuthenticationPrincipal Person person,
            String report,
            @PathVariable("{idCall}") long idCall) {
        return masterService.createReport(report, idCall, person);
    }

    @GetMapping("/addOffer/{{idKeyCar}}")
    public String addOffer(
            Map<String, Object> model,
            @PathVariable("{idKeyCar}") String idKeyCar
    ) {
        model.put("idKeyCar", idKeyCar);
        return "createOffer";
    }

    @PostMapping("/addOffer/{{idKeyCar}}")
    public String addOffer(
            String offer,
            @AuthenticationPrincipal Person person,
            @PathVariable("{idKeyCar}") long idKeyCar) {
        return masterService.createOffer(offer, idKeyCar, person);
    }

    @GetMapping("/closeCall/{{idCall}}")
    public String closeCall(
            @PathVariable("{idCall}") String idCall,
            @AuthenticationPrincipal Person person) {
        return masterService.closeCall(idCall, person);
    }

    @GetMapping("/closeKeyCar/{{idKeyCar}}")
    public String closeKeyCar(
            @AuthenticationPrincipal Person person,
            @PathVariable("{idKeyCar}") String idKeyCar) {
        return masterService.closeKeyCar(idKeyCar, person);
    }
}
