package com.microservice1.microserviceB.db.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash(value = "otp", timeToLive = 300)
public class TempOTP {
    @Id
    private String id;

    private String otp;

    private String email;
}
