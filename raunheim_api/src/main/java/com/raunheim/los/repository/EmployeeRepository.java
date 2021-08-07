package com.raunheim.los.repository;

import com.raunheim.los.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmployeeId(String employeeId);

}
