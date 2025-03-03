package com.example.gestionemployee.repository;

import com.example.gestionemployee.model.Department;
import com.example.gestionemployee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByName(String name);
    List<Employee> findByDepartmentId(Long id);
}
