package com.multilayer.microservice.microservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@EnableFeignClients("com.multilayer.microservice.microservice.feignclient")
public class AppConfig {
}
