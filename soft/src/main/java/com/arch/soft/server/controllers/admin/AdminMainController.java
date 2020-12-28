package com.arch.soft.server.controllers.admin;

import com.arch.soft.database.model.person.Person;
import com.arch.soft.server.dto.MasterRegistrationDto;
import com.arch.soft.server.servic.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/admin")
public class AdminMainController {
    private final AdminService adminService;

    public AdminMainController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/main")
    public ResponseEntity<Map<String, Object>> mainPage(
            @AuthenticationPrincipal Person person) {
        Map<String, Object> model = new HashMap<>();
        return new ResponseEntity<>(adminService.fillMainPage(person, model), HttpStatus.OK);
    }

    @GetMapping("/masterCall/{idCall}/selectMaster")
    public ResponseEntity<Map<String, Object>> listOfMasterForCall(
            @PathVariable String idCall) {
        Map<String, Object> model = new HashMap<>();
        return new ResponseEntity<>(adminService.viewMastersForCall(idCall, model), HttpStatus.OK);
    }

    @PostMapping("/masterCall/{idCall}/selectMaster")
    public ResponseEntity<Map<String, Object>> selectMasterForCall(
            @PathVariable String idCall,
            @AuthenticationPrincipal Person person,
            @RequestBody String idMaster) {
        Map<String, Object> model = new HashMap<>();
        return new ResponseEntity<>(adminService.selectMasterForCall(idCall, person, Long.parseLong(idMaster), model), HttpStatus.OK);
    }

    @GetMapping("/keyCar/{{idKeyCar}}/selectMaster")
    public ResponseEntity<Map<String, Object>> listOfMasterForKeyCar(
            @PathVariable("{idKeyCar}") String idKeyCar
    ) {
        Map<String, Object> model = new HashMap<>();
        return new ResponseEntity<>(adminService.viewMastersForKeyCar(idKeyCar, model), HttpStatus.OK);
    }

    @PostMapping("/keyCar/{{idKeyCar}}/selectMaster")
    public ResponseEntity<Map<String, Object>> selectMasterForKeyCar(
            @PathVariable("{idKeyCar}") String idKeyCar,
            @AuthenticationPrincipal Person person,
            @RequestBody String idMaster
    ) {
        Map<String, Object> model = new HashMap<>();
            return new ResponseEntity<>(adminService.selectMasterForKeyCar(idKeyCar, person, Long.parseLong(idMaster), model), HttpStatus.OK);
    }

    @PostMapping("/masterReg")
    public ResponseEntity<Map<String, Object>> masterReg(
            @RequestBody MasterRegistrationDto registrationDto,
            @AuthenticationPrincipal Person person
    ) {
        Map<String, Object> model = new HashMap<>();
        return new ResponseEntity<>(adminService.masterReg(registrationDto, person, model), HttpStatus.OK);
    }
}
