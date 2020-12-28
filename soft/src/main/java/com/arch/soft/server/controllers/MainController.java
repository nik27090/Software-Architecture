package com.arch.soft.server.controllers;

import com.arch.soft.database.model.order.KeyCar;
import com.arch.soft.database.model.order.Offer;
import com.arch.soft.database.model.person.Person;
import com.arch.soft.database.repos.KeyCarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class MainController {
    private final KeyCarRepository keyCarRepository;

    public MainController(KeyCarRepository keyCarRepository) {
        this.keyCarRepository = keyCarRepository;
    }

    @GetMapping("/")
    public String mainUser(@AuthenticationPrincipal Person person) {
        if (person.getClient() != null) {
            return "redirect:/client/mainUser";
        } else if (person.getAdmin() != null) {
            return "redirect:/admin/main";
        } else if (person.getMaster() != null) {
            return "redirect:/master/main";
        }
        return "login";
    }

    @GetMapping("/keyCar/offers/{{idKeyCar}}")
    public ResponseEntity<Map<String, Object>> viewOffers(
            @PathVariable("{idKeyCar}") String idKeyCar) {
        Map<String, Object> model = new HashMap<>();
        KeyCar keyCar = keyCarRepository.findById(Long.parseLong(idKeyCar)).get();
        List<Offer> offers = keyCar.getOffers();
        List<String> offersResponse = convertOffers(offers);

        model.put("offers", offersResponse);
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    private List<String> convertOffers(List<Offer> offers) {
        List<String> offersResponse = new ArrayList<>();

        for (Offer offer : offers) {
            if (offer.getConclusion() == null) {
                continue;
            }
            offersResponse.add(offer.getConclusion());
        }
        return offersResponse;
    }
}
