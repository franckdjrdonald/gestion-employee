package com.example.gestionemployee.controller;

import com.example.gestionemployee.model.Employee;
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

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

//    @GetMapping ("/")
//    public String showPrincipalmenu (Model model)
//    {
//        return "redirect:/index";
//    }

    @GetMapping ("/home")
    public String showHome (Model model)
    {
        return "/index";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees/create";
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult result,
                               Model model) {
        // Vérifier les erreurs de validation
        if (result.hasErrors()) {
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
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
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
            return "employees/update";
        }

        // Sauvegarder l'employé
        employeeService.save(employee);
        return "/employees/update";
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
        return "redirect:/employees/list";
    }
}
