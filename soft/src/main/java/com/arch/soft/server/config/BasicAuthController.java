package com.arch.soft.server.config;

import com.arch.soft.database.model.Role;
import com.arch.soft.database.model.person.Person;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class BasicAuthController {

    @GetMapping(path = "/basicauth")
    public Role basicauth(@AuthenticationPrincipal Person person) {
        return person.getRoles().get(0);
    }
}
