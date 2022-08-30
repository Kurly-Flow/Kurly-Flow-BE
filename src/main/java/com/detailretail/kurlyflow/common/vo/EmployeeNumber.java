package com.detailretail.kurlyflow.common.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeNumber {

  private String employeeNumber;

  public EmployeeNumber(String employeeNumber) {
    if(employeeNumber.length()!=4) {
      throw new IllegalArgumentException();
    }
    this.employeeNumber = employeeNumber;
  }

  public static EmployeeNumber of(String employeeNumber) {
    return new EmployeeNumber(employeeNumber);
  }

  public String getEmployeeNumber() {
    return employeeNumber;
  }

}
