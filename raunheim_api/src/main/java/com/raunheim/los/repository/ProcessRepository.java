package com.raunheim.los.repository;

import com.raunheim.los.model.Process;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<Process, Long> {
}
