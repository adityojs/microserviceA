package com.microservice1.microserviceA.db.repository;

import com.microservice1.microserviceA.db.entity.TempAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempAccountRepository extends CrudRepository<TempAccount, Long> {
    TempAccount getFirstByEmail(String email);
}
