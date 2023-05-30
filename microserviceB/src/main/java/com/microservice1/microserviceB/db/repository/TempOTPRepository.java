package com.microservice1.microserviceB.db.repository;

import com.microservice1.microserviceB.db.entity.TempOTP;
import org.springframework.data.repository.CrudRepository;

public interface TempOTPRepository extends CrudRepository<TempOTP, String> {
    TempOTP getFirstByEmail(String email);
}
