package com.example.EmployeePayroll.dto;

public class EmployeeDTO {

    private String name;
    private double salary;
    private String department;

    // Constructors
    public EmployeeDTO() {}

    public EmployeeDTO(String name, double salary, String department) {
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
