package com.example.gestionemployee.controller;

import com.example.gestionemployee.model.Department;
import com.example.gestionemployee.model.Employee;
import com.example.gestionemployee.service.DepartmentService;
import com.example.gestionemployee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @GetMapping ("/")
    public String showPrincipalmenu (Model model)
    {
        return "redirect:/index";
    }

    @GetMapping ("/home")
    public String showHome (Model model)
    {
        return "/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, @ModelAttribute("employee") Employee employee) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.findAll());
        return "employees/create";
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult result,
                               Model model) {
        // Vérifier les erreurs de validation
        if (result.hasErrors()) {
            List<Department> departments = departmentService.findAll();
            model.addAttribute("departments", departments);
            employee.setSalary(0.0);
            return "employees/create";
        }

        // Sauvegarder l'employé
        employeeService.save(employee);
        return "/index";
    }

    //Afficher la page de modification
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Optional<Employee> employee = employeeService.findById(id);
        List<Department> departments = departmentService.findAll();
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            model.addAttribute("departments", departments);
        }
        return "employees/update";
    }
    //Enregistrer les modifications apportees
    @PostMapping("/saveUpdate")
    public String updateEmployee(@Valid @ModelAttribute("employee") Employee employee,
                                 BindingResult result,
                                 Model model) {
        // Vérifier les erreurs de validation
        if (result.hasErrors()) {
            List<Department> departments = departmentService.findAll();
            model.addAttribute("departments", departments);
            employee.setSalary(0.0);
            return "employees/update";
        }

        // Sauvegarder l'employé
        employeeService.save(employee);
        return "/index";
    }

    // Afficher la liste des employés
    @GetMapping("/list")
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "employees/list";
    }

    @GetMapping("/delete/{id}")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {

        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "employees/delete";
        }

        return "redirect:/employees/list";
    }

    @PostMapping("/confirmDelete/{id}")
    public String deleteEmployee(@RequestParam Long id) {
        employeeService.deleteById(id);
        return "/index";
    }
}
