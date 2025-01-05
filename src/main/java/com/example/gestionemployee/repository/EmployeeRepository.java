package com.example.gestionemployee.repository;

import com.example.gestionemployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existedByName(String name);

    boolean existsByName(String name);
}
