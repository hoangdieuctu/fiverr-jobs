package com.jeespb.databaseservice.repository;

import com.jeespb.databaseservice.model.Reward;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends CrudRepository<Reward, Integer>, JpaSpecificationExecutor<Reward> {

    List<Reward> findByUsername(String username);

}
