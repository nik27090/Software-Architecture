package com.arch.soft.server.controllers.client;

import com.arch.soft.server.dto.ClientRegistrationDto;
import com.arch.soft.server.servic.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/client")
public class RegistrationController {

    private final ClientService clientService;

    public RegistrationController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody ClientRegistrationDto requestClient) {
        Map<String, Object> model = new HashMap<>();
        return new ResponseEntity<>(clientService.regClient(requestClient, model), HttpStatus.OK);
    }
}
