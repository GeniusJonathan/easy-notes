package com.example.easynotes.repository;

import com.example.easynotes.model.Employee;
import com.example.easynotes.model.EmployeeIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, EmployeeIdentity> {

    // Spring Data JPA will automaticallly parse this method name and create a query from it
    List<Employee> findByEmployeeIdentityCompanyId(String companyId);
}
