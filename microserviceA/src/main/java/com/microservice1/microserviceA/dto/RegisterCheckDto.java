package com.microservice1.microserviceA.dto;

import lombok.Data;

@Data
public class RegisterCheckDto {
    private String id;
    private String email;
    private String password;
}
