package com.example.gestionemployee.repository;

import com.example.gestionemployee.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByName(String name);
}
