package com.multilayer.microservice.microservice.service;

import com.multilayer.microservice.microservice.db.entity.Account;
import com.multilayer.microservice.microservice.db.entity.TempAccount;
import com.multilayer.microservice.microservice.db.repository.AccountRepository;
import com.multilayer.microservice.microservice.db.repository.TempAccountRepository;
import com.multilayer.microservice.microservice.dto.RegisterCheckDto;
import com.multilayer.microservice.microservice.feignclient.OTPClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TempAccountRepository tempAccountRepository;
    private final OTPClient otpClient;
    @Autowired
    public AccountService(AccountRepository accountRepository, TempAccountRepository tempAccountRepository, OTPClient otpClient) {
        this.accountRepository = accountRepository;
        this.tempAccountRepository = tempAccountRepository;
        this.otpClient = otpClient;
    }

    public ResponseEntity<?> registerCheck(RegisterCheckDto registerCheckDto) {
        String email = registerCheckDto.getEmail();
        //Data di Mysql
        Account accountByEmail = accountRepository.getFirstByEmail(email);
        if (accountByEmail!=null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        //Cek Redis
        TempAccount tempAccountByEmail = tempAccountRepository.getFirstByEmail(email);
        if (tempAccountByEmail!=null)
            return ResponseEntity.ok().build();

        //Simpan ke temp redis
        tempAccountByEmail = new TempAccount();
        tempAccountByEmail.setEmail(email);
        tempAccountByEmail.setValid(false);
        tempAccountRepository.save(tempAccountByEmail);

        // request OTP
        try {
            otpClient.requestOTP(registerCheckDto);
        } catch (FeignException.FeignClientException ex) {
            return ResponseEntity.status(ex.status()).body(ex.contentUTF8());
        }

        return ResponseEntity.ok().build();
    }
}
