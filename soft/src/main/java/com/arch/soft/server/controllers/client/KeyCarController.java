package com.arch.soft.server.controllers.client;

import com.arch.soft.database.model.person.Person;
import com.arch.soft.server.dto.MasterCallDto;
import com.arch.soft.server.servic.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
public class KeyCarController {

    private final ClientService clientService;

    public KeyCarController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/keyCar")
    public ResponseEntity<Map<String, Object>> createKeyCar(
            @RequestBody MasterCallDto requestMasterCall,
            @AuthenticationPrincipal Person person
    ) {
        Map<String, Object> model = new HashMap<>();
        return new ResponseEntity<>(clientService.createKeyCar(requestMasterCall, person, model), HttpStatus.OK);
    }
}
