package com.jeespb.databaseservice.repository;

import com.jeespb.databaseservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {

    List<Transaction> findByAccountNumber(String accountNumber);

}
