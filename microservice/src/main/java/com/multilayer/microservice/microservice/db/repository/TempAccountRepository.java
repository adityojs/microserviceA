package com.multilayer.microservice.microservice.db.repository;

import com.multilayer.microservice.microservice.db.entity.TempAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempAccountRepository extends CrudRepository<TempAccount, String> {
    TempAccount getFirstByEmail(String email);
}
