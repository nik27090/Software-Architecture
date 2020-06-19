package com.arch.soft.server.controllers.client;

import com.arch.soft.server.dto.ClientRegistrationDto;
import com.arch.soft.server.servic.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/client")
public class RegistrationController {

    private final ClientService clientService;

    public RegistrationController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(ClientRegistrationDto requestClient, Map<String, Object> model) {
        return clientService.regClient(requestClient, model);
    }
}
