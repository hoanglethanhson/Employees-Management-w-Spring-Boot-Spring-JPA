package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping("/employee-save")
    public String createEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-save";
    }

    @RequestMapping("/employee-create")
    public String doCreateEmployee(@ModelAttribute("Employee") Employee employee, Model model) {
        this.employeeRepository.save(employee);
        model.addAttribute("employeeList", employeeRepository.findAll());
        return "employee-list";
    }

    @RequestMapping(value = {"/employee-list", "/"})
    public String showAllEmployees(Model model) {
        Iterable<Employee> employees = this.employeeRepository.findAll();
        model.addAttribute("employeeList", employees);
        return "employee-list";
    }

    @RequestMapping("/employee-edit/{id}")
    public String updateEmployee(Model model, @PathVariable Long id) {
        Optional<Employee> employee = this.employeeRepository.findById(id);
        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
        }
        return "employee-update";
    }

    @RequestMapping("employee-update")
    public String doUpdateEmployee(@ModelAttribute("Employee") Employee employee, Model model) {
        this.employeeRepository.save(employee);
        model.addAttribute("employeeList", employeeRepository.findAll());
        return "employee-list";
    }

    @GetMapping("/employee/view/{id}")
    public String showFullNameLike(@PathVariable long id, Model model) {
        Optional<Employee> employee = this.employeeRepository.findById(id);
        model.addAttribute("employee", employee.get());
        return "employee-view";
    }

    @RequestMapping("/employee/delete/{id}")
    public String deleteAll(@PathVariable Long id, Model model) {
        this.employeeRepository.deleteById(id);
        model.addAttribute("employeeList", employeeRepository.findAll());
        return "employee-list";
    }
}
