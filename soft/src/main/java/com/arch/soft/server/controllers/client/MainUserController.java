package com.arch.soft.server.controllers.client;

import com.arch.soft.database.model.person.Person;
import com.arch.soft.server.servic.ClientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/client")
@PreAuthorize("hasAuthority('USER')")
public class MainUserController {

    private final ClientService clientService;

    public MainUserController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/mainUser")
    public String mainController() {
        return "mainUser";
    }

    @GetMapping("/orders")
    public String orders(
            @AuthenticationPrincipal Person person,
            Map<String, Object> model) {

        return clientService.viewOrders(person, model);
    }

    @GetMapping("/stars/{{idCall}}")
    public String selectStars(
            @PathVariable("{idCall}") String idCall,
            Map<String, Object> model) {

        return clientService.viewStars(idCall, model);
    }

    @GetMapping("/starsForCall/{{star}}/{{idCall}}")
    public String getStarsCall(
            @AuthenticationPrincipal Person person,
            @PathVariable("{star}") int star,
            @PathVariable("{idCall}") long idCall) {

        return clientService.getStarsForCall(person, star, idCall);
    }

    @GetMapping("/starsForKeyCar/{{star}}/{{idKeyCar}}")
    public String getStarsKeyCar(
            @AuthenticationPrincipal Person person,
            @PathVariable("{star}") int star,
            @PathVariable("{idKeyCar}") long idCall) {

        return clientService.getStarsForKeyCar(person, star, idCall);
    }
}
