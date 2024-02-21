package com.springTesting.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springTesting.dto.CreateEmployeeDto;
import com.springTesting.service.EmployeeService;

@WebMvcTest
public class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  @Autowired
  private ObjectMapper objectMapper;

  private CreateEmployeeDto createEmployeeDto;

   @BeforeEach
  public void init(){
    createEmployeeDto = new CreateEmployeeDto();
    createEmployeeDto.setName("Pedro");
    createEmployeeDto.setLastName("lopez");
    createEmployeeDto.setEmail("lopez@gmail.com");
  }

  @Test
  void testCreateEmployee() throws Exception  {
    given(employeeService.saveEmployee(createEmployeeDto))
      .willAnswer(invocation -> invocation);
    
    ResultActions response = mockMvc.perform(post("/employee")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(createEmployeeDto)));

    response.andDo(print())
      .andExpect(status().isCreated());
  }

  @Test
  void testDeleteEmployee() {

  }

  @Test
  void testGetEmployeeById() {

  }

  @Test
  void testGetEmployees() {

  }

  @Test
  void testUpdateEmployee() {

  }
}
