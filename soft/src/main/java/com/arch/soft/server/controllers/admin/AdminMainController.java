package com.arch.soft.server.controllers.admin;

import com.arch.soft.database.model.person.Person;
import com.arch.soft.server.dto.MasterRegistrationDto;
import com.arch.soft.server.servic.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminMainController {
    private final AdminService adminService;

    public AdminMainController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/main")
    public String mainPage(
            @AuthenticationPrincipal Person person,
            Map<String, Object> model) {

        return adminService.fillMainPage(person, model);
    }

    @GetMapping("/masterCall/{idCall}/selectMaster")
    public String listOfMasterForCall(@PathVariable String idCall, Map<String, Object> model) {

        return adminService.viewMastersForCall(idCall, model);
    }

    @PostMapping("/masterCall/{idCall}/selectMaster")
    public String selectMasterForCall(
            @PathVariable String idCall,
            @AuthenticationPrincipal Person person,
            Long idMaster,
            Map<String, Object> model) {

        return adminService.selectMasterForCall(idCall, person, idMaster, model);
    }

    @GetMapping("/keyCar/{{idKeyCar}}/selectMaster")
    public String listOfMasterForKeyCar(
            @PathVariable("{idKeyCar}") String idKeyCar,
            Map<String, Object> model
    ) {
        return adminService.viewMastersForKeyCar(idKeyCar, model);
    }

    @PostMapping("/keyCar/{{idKeyCar}}/selectMaster")
    public String selectMasterForKeyCar(
            @PathVariable("{idKeyCar}") String idKeyCar,
            @AuthenticationPrincipal Person person,
            Long idMaster,
            Map<String, Object> model) {
        return adminService.selectMasterForKeyCar(idKeyCar, person, idMaster, model);
    }

    @GetMapping("/masterReg")
    public String masterReg() {
        return "masterReg";
    }

    @PostMapping("/masterReg")
    public String masterReg(
            MasterRegistrationDto registrationDto,
            @AuthenticationPrincipal Person person,
            Map<String, Object> model
    ) {
        return adminService.masterReg(registrationDto, person, model);
    }
}