package com.arch.soft.server.controllers.client;

import com.arch.soft.database.model.person.Person;
import com.arch.soft.server.servic.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/client")
public class MainUserController {

    private final ClientService clientService;

    public MainUserController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> orders(
            @AuthenticationPrincipal Person person
    ) {
        Map<String, Object> model = new HashMap<>();
        return new ResponseEntity<>(clientService.viewOrders(person, model), HttpStatus.OK);
    }

    @GetMapping("/starsForCall/{{star}}/{{idCall}}")
    public ResponseEntity<Integer> getStarsCall(
            @AuthenticationPrincipal Person person,
            @PathVariable("{star}") int star,
            @PathVariable("{idCall}") long idCall) {

        return new ResponseEntity<>(clientService.getStarsForCall(person, star, idCall), HttpStatus.OK);
    }

    @GetMapping("/starsForKeyCar/{{star}}/{{idKeyCar}}")
    public ResponseEntity<Integer> getStarsKeyCar(
            @AuthenticationPrincipal Person person,
            @PathVariable("{star}") int star,
            @PathVariable("{idKeyCar}") long idCall) {

        return new ResponseEntity<>(clientService.getStarsForKeyCar(person, star, idCall), HttpStatus.OK);
    }
}
