package com.microservice1.microserviceA.feignclient;

import com.microservice1.microserviceA.dto.RegisterCheckDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "otp", url = "http://localhost:8020")
public interface OTPClient {
    @PostMapping("/request")
    ResponseEntity<?> requestOTP(@RequestBody RegisterCheckDto registerCheckDto);
}
