package com.springTesting.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springTesting.dto.CreateEmployeeDto;
import com.springTesting.dto.UpdateEmployeedDto;
import com.springTesting.exception.ResourceNotFoundException;
import com.springTesting.model.Employee;
import com.springTesting.repository.EmployeeRepository;
import com.springTesting.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

  @Autowired
  private EmployeeRepository employeeRepository;

  @Override
  public Employee saveEmployee(CreateEmployeeDto employee) {
    Optional<Employee> employeeSaved = employeeRepository.findByEmail(employee.getEmail());
    if( employeeSaved.isPresent() ){
      throw new ResourceNotFoundException("Employee with email " + employee.getEmail() + " is already saved");
    }

    Employee newEmployee = Employee.builder()
      .email(employee.getEmail())
      .name(employee.getName())
      .lastName(employee.getLastName())
      .build();

    return employeeRepository.save(newEmployee);
  }

  @Override
  public List<Employee> getAllEmployee() {
    return employeeRepository.findAll();
  }

  @Override
  public Optional<Employee> getEmployeeById(Long id) {
    return employeeRepository.findById(id);
  }

  @Override
  public Employee updateEmployee(UpdateEmployeedDto employee, Long id) {
    Optional<Employee> employeeSaved = employeeRepository.findById(id);

    if( employeeSaved.isEmpty() ){
      throw new ResourceNotFoundException("Employee with Id " + id + " not Found");
    }

    Employee newEmployee = Employee.builder()
    .email(employee.getEmail())
    .name(employee.getName())
    .lastName(employee.getLastName())
    .build();
    
    return employeeRepository.save(newEmployee);
  }

  @Override
  public void deleteEmployee(Long id) {
    employeeRepository.deleteById(id);
  }
    
}