package com.jeespb.databaseservice.repository;

import com.jeespb.databaseservice.model.Account;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

    List<Account> findByUsername(String username);

}
