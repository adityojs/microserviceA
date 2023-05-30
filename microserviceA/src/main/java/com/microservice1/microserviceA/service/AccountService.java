package com.microservice1.microserviceA.service;

import com.microservice1.microserviceA.db.entity.Account;
import com.microservice1.microserviceA.db.entity.TempAccount;
import com.microservice1.microserviceA.db.repository.AccountRepository;
import com.microservice1.microserviceA.db.repository.TempAccountRepository;
import com.microservice1.microserviceA.dto.RegisterCheckDto;
import com.microservice1.microserviceA.feignclient.OTPClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

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
        // cek data mysql
        Account accountByEmail = accountRepository.getFirstByEmail(email);
        if (accountByEmail!=null)
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        String randomOTP = generateOTP();

        // cek data redis
        TempAccount tempAccountByEmail = tempAccountRepository.getFirstByEmail(email);
        if (tempAccountByEmail!=null)
            return ResponseEntity.ok().build();
        //simpan ke temp redis
        tempAccountByEmail = new TempAccount();
        tempAccountByEmail.setEmail(email);
        tempAccountByEmail.setOtp(randomOTP);
        tempAccountByEmail.setValid(false);
        tempAccountRepository.save(tempAccountByEmail);

        //req OTP
        try {
            otpClient.requestOTP(registerCheckDto);
        } catch (FeignException.FeignClientException.FeignClientException ex){
            return ResponseEntity.status(ex.status()).body(ex.contentUTF8());
        }

        return ResponseEntity.ok(tempAccountByEmail);
    }
    private String generateOTP() {
        return new DecimalFormat("0000").format(new Random().nextInt(9999));
    }
}
