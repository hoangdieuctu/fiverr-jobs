package com.raunheim.los.service;

import com.raunheim.los.model.Employee;
import com.raunheim.los.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee getOrCreate(String employeeId) {
        var employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee != null) {
            return employee;
        }

        log.info("Create a new employee: {}", employeeId);
        var newEmployee = new Employee();
        newEmployee.setEmployeeId(employeeId);

        employeeRepository.save(newEmployee);
        return newEmployee;
    }

}
