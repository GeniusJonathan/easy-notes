package com.example.easynotes.service;

import com.example.easynotes.model.Employee;
import com.example.easynotes.model.EmployeeIdentity;
import com.example.easynotes.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Optional<Employee> findEmployee(String employeeId, String companyId) {
        return employeeRepository.findById(new EmployeeIdentity(employeeId, companyId));
    }
}
