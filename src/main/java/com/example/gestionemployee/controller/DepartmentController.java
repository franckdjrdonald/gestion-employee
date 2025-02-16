package com.example.gestionemployee.controller;

import com.example.gestionemployee.model.Department;
import com.example.gestionemployee.model.Employee;
import com.example.gestionemployee.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/departments")
public class DepartmentController {
    public final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/create")
    public String showCreateForm(org.springframework.ui.Model model, @ModelAttribute("department") Department department) {
        model.addAttribute("department", new Department());
        return "/departments/create";
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("department") Department department,
                               BindingResult result,
                               org.springframework.ui.Model model) {
        // Vérifier les erreurs de validation
        if (result.hasErrors()) {
            model.addAttribute("department", new Department());
            return "/departments/create";
        }

        // Sauvegarder le department
        departmentService.save(department);
        return "/departments/list";
    }
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Optional<Department> department = departmentService.findById(id);
        if (department.isPresent()) {
            model.addAttribute("department", department.get());
            return "/departments/update";
        }
        return "/departments/list";
    }
    //Enregistrer les modifications apportees
    @PostMapping("/saveUpdate")
    public String updateDepartment(@Valid @ModelAttribute("department") Department department,
                                   BindingResult result,
                                   org.springframework.ui.Model model) {
        // Vérifier les erreurs de validation
        if (result.hasErrors()) {
            model.addAttribute("department", new Department());
            return "/departments/create";
        }

        // Sauvegarder le department
        departmentService.save(department);
        return "/departments/list";
    }

    // Afficher la liste des departements
    @GetMapping("/list")
    public String listEmployees(Model model) {
        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments", departments);
        return "/departments/list";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {

        Optional<Department> department = departmentService.findById(id);
        if (department.isPresent()) {
            model.addAttribute("department", department.get());
            return "/departments/delete";
        }
        return "/departments/list";
    }
    //Confirmer la suppression du departement
    @PostMapping("/confirmDelete/{id}")
    public String deleteDeparment(@RequestParam Long id) {
        departmentService.deleteById(id);
        return "redirect:/departments/list";
    }
}
