package com.example.EmployeePayroll.service;

import com.example.EmployeePayroll.exception.ResourceNotFoundException;
import com.example.EmployeePayroll.model.Employee;
import com.example.EmployeePayroll.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Long id) {
        Employee existingEmployee = getEmployeeById(id);
        employeeRepository.delete(existingEmployee);
    }
}
