package com.example.EmployeePayroll.service;

import com.example.EmployeePayroll.dto.EmployeeDTO;
import com.example.EmployeePayroll.exception.ResourceNotFoundException;
import com.example.EmployeePayroll.model.Employee;
import com.example.EmployeePayroll.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j // Lombok Logger
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeDTO> getAllEmployees() {
        log.info("Fetching all employees");
        return employeeRepository.findAll()
                .stream()
                .map(emp -> new EmployeeDTO(emp.getName(), emp.getSalary(), emp.getDepartment()))
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        log.info("Fetching employee with ID: {}", id);
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", id);
                    return new ResourceNotFoundException("Employee not found with ID: " + id);
                });
        return new EmployeeDTO(emp.getName(), emp.getSalary(), emp.getDepartment());
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        log.info("Adding new employee: {}", employeeDTO.getName());
        Employee emp = new Employee(employeeDTO.getName(), employeeDTO.getSalary(), employeeDTO.getDepartment());
        Employee savedEmp = employeeRepository.save(emp);
        log.debug("Employee saved successfully with ID: {}", savedEmp.getId());
        return new EmployeeDTO(savedEmp.getName(), savedEmp.getSalary(), savedEmp.getDepartment());
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        log.info("Updating employee with ID: {}", id);
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setSalary(employeeDTO.getSalary());
        existingEmployee.setDepartment(employeeDTO.getDepartment());

        Employee updatedEmp = employeeRepository.save(existingEmployee);
        log.debug("Employee updated successfully with ID: {}", updatedEmp.getId());
        return new EmployeeDTO(updatedEmp.getName(), updatedEmp.getSalary(), updatedEmp.getDepartment());
    }

    public void deleteEmployee(Long id) {
        log.warn("Attempting to delete employee with ID: {}", id);
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with ID: {}", id);
                    return new ResourceNotFoundException("Employee not found with ID: " + id);
                });

        employeeRepository.delete(existingEmployee);
        log.info("Employee with ID: {} deleted successfully", id);
    }
}
