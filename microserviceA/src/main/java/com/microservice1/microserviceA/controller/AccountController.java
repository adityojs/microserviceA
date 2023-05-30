package com.microservice1.microserviceA.controller;

import com.microservice1.microserviceA.dto.RegisterCheckDto;
import com.microservice1.microserviceA.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class AccountController {
    private final AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/check")
    public ResponseEntity<?> registerCheck(@RequestBody RegisterCheckDto registerCheckDto) {
        log.debug("register {}", registerCheckDto);
        return accountService.registerCheck(registerCheckDto);
    }
}
