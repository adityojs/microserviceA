package com.microservice1.microserviceB.service;

import com.microservice1.microserviceB.db.entity.TempOTP;
import com.microservice1.microserviceB.db.repository.TempOTPRepository;
import com.microservice1.microserviceB.dto.RegisterCheckDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

@Log4j2
@Service
public class OTPService {
    private final TempOTPRepository tempOTPRepository;
    @Autowired
    public OTPService(TempOTPRepository tempOTPRepository) {
        this.tempOTPRepository = tempOTPRepository;
    }

    public void requestOTP(RegisterCheckDto registerCheckDto) {
        String email = registerCheckDto.getEmail();
        // cek otp redis
        TempOTP tempOTPByEmail =  tempOTPRepository.getFirstByEmail(email);
        if (tempOTPByEmail!=null) {
            tempOTPRepository.delete(tempOTPByEmail);
        }
        // generate otp
        String randomOTP = generateOTP();
        log.debug("random otp:", randomOTP);
        // save ke redis
        TempOTP tempOTP = new TempOTP();
        tempOTP.setEmail(email);
        tempOTP.setOtp(randomOTP);
        tempOTPRepository.save(tempOTP);
    }

    private String generateOTP() {
        return new DecimalFormat("0000").format(new Random().nextInt(9999));
    }
}
