package com.example.gestionemployee.service;

import com.example.gestionemployee.model.Department;
import com.example.gestionemployee.model.Employee;
import com.example.gestionemployee.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Optional<Department> findById(Long id) {
        return departmentRepository.findById(id);
    }

    public void save(Department department) {
        departmentRepository.save(department);
    }

    public void deleteById(Long id) {
        departmentRepository.deleteById(id);
    }

    public boolean existsByName(String name) {
        return departmentRepository.existsByName(name);
    }
}
