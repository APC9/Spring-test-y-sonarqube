package com.springTesting.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springTesting.dto.CreateEmployeeDto;
import com.springTesting.dto.UpdateEmployeedDto;
import com.springTesting.model.Employee;
import com.springTesting.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    
  private final EmployeeService employeeService;

  public EmployeeController( EmployeeService employeeService ) {
    this.employeeService = employeeService;
  }

  @PostMapping
  @ResponseStatus( HttpStatus.CREATED )
  public Employee createEmployee(@RequestBody CreateEmployeeDto employee) {
    return employeeService.saveEmployee(employee);
  }

  @GetMapping
  public List<Employee> getEmployees() {
    return employeeService.getAllEmployee();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    return employeeService.getEmployeeById(id)
      .map(ResponseEntity::ok)
      .orElseGet( ()-> ResponseEntity.notFound().build()); 
  }

  @PutMapping("/{id}")
  public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody UpdateEmployeedDto updateEmployeedDto) {
    Employee employee = employeeService.updateEmployee(updateEmployeedDto, id);
    return new ResponseEntity<>(employee, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
    employeeService.deleteEmployee(id);
    return new ResponseEntity<>("Employee with id " +id+ " deleted successfully", HttpStatus.OK);
  }

}