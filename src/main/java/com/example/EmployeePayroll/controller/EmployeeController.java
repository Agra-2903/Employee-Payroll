package com.example.EmployeePayroll.controller;

import com.example.EmployeePayroll.dto.EmployeeDTO;
import com.example.EmployeePayroll.model.Employee;
import com.example.EmployeePayroll.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend access
public class EmployeeController {

    private final EmployeeService employeeService;

    // UC4: Used autowired
    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Get all employees
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    // Add new employee
    @PostMapping
    public EmployeeDTO addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.addEmployee(employeeDTO);
    }

    // Update existing employee
    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id,@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(id, employeeDTO);
    }

    // Delete employee
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
