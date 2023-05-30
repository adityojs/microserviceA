package com.microservice1.microserviceA.db.repository;

import com.microservice1.microserviceA.db.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getFirstByEmail(String email);
}
