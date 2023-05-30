package com.microservice1.microserviceA.db.entity;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash(value = "account", timeToLive = 3600)
public class TempAccount {
    @Id
    private String id;
    private String email;
    private String otp;
    private boolean valid = false;

}
