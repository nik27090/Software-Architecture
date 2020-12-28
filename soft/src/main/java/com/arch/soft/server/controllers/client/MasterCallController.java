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
@RequestMapping("/api/v1/client/")
@CrossOrigin(origins = "http://localhost:4200")
public class MasterCallController {

    private final ClientService clientService;

    public MasterCallController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping(path = "/masterCall")
    public ResponseEntity<Map<String, Object>> createCall(
            @RequestBody MasterCallDto requestMasterCall,
            @AuthenticationPrincipal Person person
    ) {
        Map<String, Object> model = new HashMap<>();
        return new ResponseEntity<>(clientService.createMasterCall(requestMasterCall, person, model), HttpStatus.OK);
    }

}
