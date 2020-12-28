package com.arch.soft.server.controllers.master;

import com.arch.soft.database.model.person.Person;
import com.arch.soft.server.servic.MasterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/master")
public class MasterMainController {

    private final MasterService masterService;

    public MasterMainController(MasterService masterService) {
        this.masterService = masterService;
    }

    @GetMapping("/newOrders")
    public ResponseEntity<Map<String, Object>> newOrders(
            @AuthenticationPrincipal Person person) {
        Map<String, Object> model = new HashMap<>();
        return new ResponseEntity<>(masterService.viewNewOrders(person, model), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> orders(
            @AuthenticationPrincipal Person person
    ) {
        Map<String, Object> model = new HashMap<>();
        return new ResponseEntity<>(masterService.viewOrders(person, model), HttpStatus.OK);
    }

    @GetMapping("/masterCall/{{idCall}}/ok")
    public ResponseEntity<Map<String, Object>> agreeCall(
            @AuthenticationPrincipal Person person,
            @PathVariable("{idCall}") String idCall) {
        return new ResponseEntity<>(masterService.takeOrderCall(Long.parseLong(idCall), person), HttpStatus.OK);
    }

    @GetMapping("/masterCall/{{idCall}}/cancel")
    public ResponseEntity<Map<String, Object>> disagreeCall(
            @AuthenticationPrincipal Person person,
            @PathVariable("{idCall}") String idCall) {
        return new ResponseEntity<>(masterService.dontTakeOrderCall(Long.parseLong(idCall), person), HttpStatus.OK);
    }

    @GetMapping("/keyCar/{{idKeyCar}}/ok")
    public ResponseEntity<Map<String, Object>> agreeKeyCar(
            @AuthenticationPrincipal Person person,
            @PathVariable("{idKeyCar}") String idKeyCar) {
        return new ResponseEntity<>(masterService.takeOrderKeyCar(Long.parseLong(idKeyCar), person), HttpStatus.OK);
    }

    @GetMapping("/keyCar/{{idKeyCar}}/cancel")
    public ResponseEntity<Map<String, Object>> disagreeKeyCar(
            @AuthenticationPrincipal Person person,
            @PathVariable("{idKeyCar}") String idKeyCar) {
        return new ResponseEntity<>(masterService.dontTakeOrderKeyCar(Long.parseLong(idKeyCar), person), HttpStatus.OK);
    }

    @PostMapping("/report/{{idCall}}")
    public String createReportCall(
            @AuthenticationPrincipal Person person,
            @RequestBody String report,
            @PathVariable("{idCall}") long idCall) {
        return masterService.createReport(report, idCall, person);
    }

    @PostMapping("/addOffer/{{idKeyCar}}")
    public ResponseEntity<Map<String, Object>> addOffer(
            @RequestBody String offer,
            @AuthenticationPrincipal Person person,
            @PathVariable("{idKeyCar}") String idKeyCar) {
        return new ResponseEntity<>(masterService.createOffer(offer, Long.parseLong(idKeyCar), person), HttpStatus.OK);
    }

    @GetMapping("/closeCall/{{idCall}}")
    public ResponseEntity<Map<String, Object>> closeCall(
            @PathVariable("{idCall}") String idCall,
            @AuthenticationPrincipal Person person) {
        return new ResponseEntity<>(masterService.closeCall(idCall, person), HttpStatus.OK);
    }

    @GetMapping("/closeKeyCar/{{idKeyCar}}")
    public ResponseEntity<Map<String, Object>> closeKeyCar(
            @AuthenticationPrincipal Person person,
            @PathVariable("{idKeyCar}") String idKeyCar) {
        return new ResponseEntity<>(masterService.closeKeyCar(idKeyCar, person), HttpStatus.OK);
    }
}
