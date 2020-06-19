package com.arch.soft.server.controllers.client;

import com.arch.soft.database.model.person.Person;
import com.arch.soft.server.dto.MasterCallDto;
import com.arch.soft.server.servic.ClientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/client")
@PreAuthorize("hasAuthority('USER')")
public class MasterCallController {

    private final ClientService clientService;

    public MasterCallController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/masterCall")
    public String materCall() {
        return "masterCall";
    }

    @PostMapping("/masterCall")
    public String createCall(
            MasterCallDto requestMasterCall,
            @AuthenticationPrincipal Person person,
            Map<String, Object> model
    ) {
        return clientService.createMasterCall(requestMasterCall, person, model);
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

}
