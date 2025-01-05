package com.example.gestionemployee.controller;

import com.example.gestionemployee.model.Employee;
import com.example.gestionemployee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list";
    }

    @GetMapping("/employees/create")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees/create";
    }

    @PostMapping("/employees/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult result,
                               Model model) {
        // Vérifier les erreurs de validation
        if (result.hasErrors()) {
            return "employees/create";
        }

        // Vérifier si le nom est unique
        if (employeeService.existsByName(employee.getName())) {
            model.addAttribute("nameError", "Le nom de l'employé doit être unique.");
            return "employees/create";
        }

        // Sauvegarder l'employé
        employeeService.save(employee);
        return "redirect:/employees";
    }

    // Afficher le formulaire de modification
    @GetMapping("/employees/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        // Récupérer l'employé par ID
        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isEmpty()) {
            throw new IllegalArgumentException("Employé avec l'ID " + id + " introuvable.");
        }
        model.addAttribute("employee", employee);
        return "employees/update";
    }
    @PostMapping("/employees/saveUpdate")
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult result,
                               Model model) {
        // Vérifier les erreurs de validation
        if (result.hasErrors()) {
            return "employees/create";
        }

        // Vérifier si le nom est unique
        if (employeeService.existsByName(employee.getName())) {
            model.addAttribute("nameError", "Le nom de l'employé doit être unique.");
            return "employees/create";
        }

        // Sauvegarder l'employé
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employees ID: " + id));
        model.addAttribute("employee", employee);
        return "employees/delete";
    }

    @PostMapping("/delete")
    public String deleteEmployee(@RequestParam Long id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }
}
