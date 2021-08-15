package com.jeespb.databaseservice.repository;

import com.jeespb.databaseservice.model.Access;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AccessRepository extends ElasticsearchRepository<Access, String> {
    Page<Access> findByType(String type, Pageable pageable);
}
