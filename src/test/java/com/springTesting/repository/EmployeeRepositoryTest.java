package com.springTesting.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.springTesting.model.Employee;

@DataJpaTest
public class EmployeeRepositoryTest {

  @Autowired
  private EmployeeRepository employeeRepository;

  private Employee employee;

  @BeforeEach
  public void init(){
    employee = Employee.builder()
    .name("Pepe")
    .lastName("Castillo")
    .email("castillo@mail.com")
    .build();
  }

  @DisplayName("Test para Guardar un Empleado")
  @Test
  public void testSaveEmployee() {  

    Employee employeeSaved = employeeRepository.save(employee);

    assertTrue(employeeSaved != null);
    assertThat(employeeSaved).isNotNull();
    assertThat(employeeSaved.getId()).isGreaterThan(0);
  }

  @DisplayName("Listar empleados")
  @Test
  public void testGetAllEmployees(){

    Employee employee1 = Employee.builder()
    .name("maria")
    .lastName("lopez")
    .email("lopez@mail.com")
    .build();
  
    employeeRepository.save(employee1);
    employeeRepository.save(employee);

    List<Employee> employees = employeeRepository.findAll();

    assertThat(employees).isNotNull();
    assertThat(employees.size()).isEqualTo(2);
  }

  @DisplayName("Obtener empleado por id")
  @Test
  public void testGetEmployeeById(){
    Employee employeeSaved = employeeRepository.save(employee);
    Optional<Employee> employee = employeeRepository.findById(employeeSaved.getId());
    assertThat(employee.isPresent()).isTrue();
  }

  @DisplayName("No debe rotarna empledo con id 99999999")
  @Test
  public void testNotGetEmployeeById(){
    Optional<Employee> employee = employeeRepository.findById(99999999L);
    assertThat(employee.isPresent()).isFalse();
  }

  @DisplayName("Obtener empleado por Email")
  @Test
  public void testGetEmployeeByEmail(){
    Employee employeeSaved = employeeRepository.save(employee);
    Optional<Employee> employee = employeeRepository.findByEmail(employeeSaved.getEmail());
    assertThat(employee.isPresent()).isTrue();
  }

  @DisplayName("Obtener empleado por id")
  @Test
  public void testUpdatedEmployeeById(){
    Employee employeeSaved = employeeRepository.save(employee);
    Employee employee = employeeRepository.findById(employeeSaved.getId()).get();

    employee.setName("Victor");

    assertThat(employee.getName()).isEqualTo("Victor");
  }

  @DisplayName("Borrar empleado por id")
  @Test
  public void testDeleteEmployeeById(){
    Employee employeeSaved = employeeRepository.save(employee);
    employeeRepository.deleteById(employeeSaved.getId());

    Optional<Employee> employee = employeeRepository.findById(employeeSaved.getId());
    assertThat(employee.isPresent()).isFalse();
  }
}
