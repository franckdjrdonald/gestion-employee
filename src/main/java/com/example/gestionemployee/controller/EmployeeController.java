package com.example.gestionemployee.controller;

import com.example.gestionemployee.model.Employee;
import com.example.gestionemployee.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "employee/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/create";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee employee, Model model) {
        try {
            employeeService.save(employee);
            return "redirect:/employees";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "employee/create";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + id));
        model.addAttribute("employee", employee);
        return "employee/edit";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + id));
        model.addAttribute("employee", employee);
        return "employee/delete";
    }

    @PostMapping("/delete")
    public String deleteEmployee(@RequestParam Long id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }
}
