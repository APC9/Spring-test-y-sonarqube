package com.springTesting.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springTesting.dto.CreateEmployeeDto;
import com.springTesting.dto.UpdateEmployeedDto;
import com.springTesting.exception.ResourceNotFoundException;
import com.springTesting.model.Employee;
import com.springTesting.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

  @Mock
  private EmployeeRepository employeeRepository;

  @InjectMocks
  private EmployeeServiceImpl employeeServiceImpl;

  private Employee employee;
  private Employee employeeUpdated;
  private CreateEmployeeDto employeeDto;
  private UpdateEmployeedDto updateEmployeedDto;

  @BeforeEach
  public void init(){
    employeeDto = new CreateEmployeeDto();
    employeeDto.setName("Pepe");
    employeeDto.setLastName("castillo");
    employeeDto.setEmail("castillo@gmail.com");

    updateEmployeedDto = new UpdateEmployeedDto();
    updateEmployeedDto.setName("Juan");

    employee = Employee.builder()
    .name(employeeDto.getName())
    .lastName(employeeDto.getLastName())
    .email(employeeDto.getEmail())
    .build();

    employeeUpdated = Employee.builder()
      .name(updateEmployeedDto.getName())
      .email(employee.getEmail())
      .lastName(employee.getLastName())
      .build();

  }

  @DisplayName("Test para Guardar un empleado")
  @Test
  public void testSaveEmployee () {
    given(employeeRepository.findByEmail(employeeDto.getEmail()))
      .willReturn(Optional.empty());
    
    // Usamos any() para aceptar cualquier instancia de Employee
    given(employeeRepository.save(any(Employee.class))).willReturn(employee); 

    Employee employeeSaved = employeeServiceImpl.saveEmployee(employeeDto);
    assertThat(employeeSaved).isNotNull();
  }

  @DisplayName("Test de exception al Guardar un empleado")
  @Test
  public void testSaveEmployeeWithThrowException () {
    given(employeeRepository.findByEmail(employeeDto.getEmail()))
      .willReturn(Optional.of(employee));
    
    // Crear la exception al intentar guardar empleado que ya existe
    assertThrows(ResourceNotFoundException.class, ()->{
      employeeServiceImpl.saveEmployee(employeeDto);
    });
   
    // Verificar que nunca se haya llamado al repositorio para guardar employee
    verify(employeeRepository, never()).save(any(Employee.class));
  }

  @DisplayName("Test Actualizar un empleado")
  @Test
  void testUpdateEmployee() {
    given(employeeRepository.findById(employee.getId()))
      .willReturn(Optional.of(employee));

    given(employeeRepository.save(any(Employee.class))).willReturn(employeeUpdated); 

    Employee employeeUpdated = employeeServiceImpl.updateEmployee(updateEmployeedDto, employee.getId());
    assertThat(employeeUpdated.getName()).isEqualTo("Juan");
  } 

  @DisplayName("Test de exception al Actualizar un empleado")
  @Test
  public void testUpdateEmployeeWithThrowException () {

    Long idNotfound = 3L; // Simular Id que no exist en BBDD
    given(employeeRepository.findById(idNotfound)).willReturn(Optional.empty());
    
    // Crear la exception al intentar guardar empleado que ya existe
    assertThrows(ResourceNotFoundException.class, ()->{
      employeeServiceImpl.updateEmployee(updateEmployeedDto, idNotfound);
    });
    
    // Verificar que nunca se haya llamado al repositorio para guardar employee
    verify(employeeRepository, never()).save(any(Employee.class));
  }
    

  @DisplayName("Test para retornar una lista de empleados")
  @Test
  void testGetAllEmployee() {
    given( employeeRepository.findAll()).willReturn(List.of(employee));
    List<Employee> employees = employeeServiceImpl.getAllEmployee();
    assertThat(employees.size()).isGreaterThan(0);
    assertThat(employees).isNotNull();
  }

  @DisplayName("Test para retornar una lista vacia")
  @Test
  void testGetListEmployeeEmpty() {
    given( employeeRepository.findAll()).willReturn(Collections.emptyList());
    List<Employee> employees = employeeServiceImpl.getAllEmployee();
    assertThat(employees.size()).isEqualTo(0);
    assertThat(employees).isEmpty();
  }

  @DisplayName("Test para obtener un employee por id")
  @Test
  void testGetEmployeeById() {
    given( employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));
    Optional<Employee> employeeSaved = employeeServiceImpl.getEmployeeById(employee.getId());
    assertThat(employeeSaved.isPresent()).isTrue();
   }


  @DisplayName("Test para elimanar un employee")
  @Test
  void testDeleteEmployee() { 
    willDoNothing().given(employeeRepository).deleteById(employee.getId());
    employeeServiceImpl.deleteEmployee(employee.getId()); 
    verify(employeeRepository, times(1)).deleteById(employee.getId());
  }

}
