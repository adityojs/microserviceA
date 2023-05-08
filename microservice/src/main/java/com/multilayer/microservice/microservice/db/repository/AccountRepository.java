package com.multilayer.microservice.microservice.db.repository;

import com.multilayer.microservice.microservice.db.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getFirstByEmail(String email);
}
