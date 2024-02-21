package com.springTesting.service;

import java.util.List;
import java.util.Optional;

import com.springTesting.dto.CreateEmployeeDto;
import com.springTesting.dto.UpdateEmployeedDto;
import com.springTesting.model.Employee;

public interface EmployeeService {
    Employee saveEmployee(CreateEmployeeDto employee);
    List<Employee> getAllEmployee();
    Optional<Employee> getEmployeeById(Long id);
    Employee updateEmployee(UpdateEmployeedDto employee,Long id);
    void deleteEmployee(Long id);
}