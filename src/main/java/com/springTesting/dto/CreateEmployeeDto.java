package com.springTesting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEmployeeDto {
  private String name;
  private String lastName;
  private String email;
}