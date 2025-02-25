package com.example.EmployeePayroll.service;

import com.example.EmployeePayroll.dto.EmployeeDTO;
import com.example.EmployeePayroll.exception.ResourceNotFoundException;
import com.example.EmployeePayroll.model.Employee;
import com.example.EmployeePayroll.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // UC4: Used autowired
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(emp -> new EmployeeDTO(emp.getName(), emp.getSalary(), emp.getDepartment()))
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        return new EmployeeDTO(emp.getName(), emp.getSalary(), emp.getDepartment());
    }

    // Add a new employee
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee emp = new Employee(employeeDTO.getName(), employeeDTO.getSalary(), employeeDTO.getDepartment());
        Employee savedEmp = employeeRepository.save(emp);
        return new EmployeeDTO(savedEmp.getName(), savedEmp.getSalary(), savedEmp.getDepartment());
    }

    // Update employee
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setSalary(employeeDTO.getSalary());
        existingEmployee.setDepartment(employeeDTO.getDepartment());
        Employee updatedEmp = employeeRepository.save(existingEmployee);
        return new EmployeeDTO(updatedEmp.getName(), updatedEmp.getSalary(), updatedEmp.getDepartment());
    }

    public void deleteEmployee(Long id) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        employeeRepository.delete(existingEmployee);
    }
}
