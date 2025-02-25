package com.example.EmployeePayroll.service;

import com.example.EmployeePayroll.dto.EmployeeDTO;
import com.example.EmployeePayroll.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final List<Employee> employeeList = new ArrayList<>();
    private long idCounter = 1;

    // Get all employees
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for (Employee emp : employeeList) {
            employeeDTOList.add(new EmployeeDTO(emp.getName(), emp.getSalary(), emp.getDepartment()));
        }
        return employeeDTOList;
    }

    // Get employee by ID
    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> employee = employeeList.stream().filter(emp -> emp.getId().equals(id)).findFirst();
        if (employee.isPresent()) {
            return new EmployeeDTO(employee.get().getName(), employee.get().getSalary(), employee.get().getDepartment());
        }
        throw new RuntimeException("Employee not found with ID: " + id);
    }

    // Add a new employee
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee newEmployee = new Employee(idCounter++, employeeDTO.getName(), employeeDTO.getSalary(), employeeDTO.getDepartment());
        employeeList.add(newEmployee);
        return new EmployeeDTO(newEmployee.getName(), newEmployee.getSalary(), newEmployee.getDepartment());
    }

    // Update employee
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        for (Employee emp : employeeList) {
            if (emp.getId().equals(id)) {
                emp.setName(employeeDTO.getName());
                emp.setSalary(employeeDTO.getSalary());
                emp.setDepartment(employeeDTO.getDepartment());
                return new EmployeeDTO(emp.getName(), emp.getSalary(), emp.getDepartment());
            }
        }
        throw new RuntimeException("Employee not found with ID: " + id);
    }

    // Delete employee
    public void deleteEmployee(Long id) {
        employeeList.removeIf(emp -> emp.getId().equals(id));
    }
}
