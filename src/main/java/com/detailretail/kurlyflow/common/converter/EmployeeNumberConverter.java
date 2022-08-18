package com.detailretail.kurlyflow.common.converter;


import com.detailretail.kurlyflow.common.vo.EmployeeNumber;
import javax.persistence.AttributeConverter;

public class EmployeeNumberConverter implements AttributeConverter<EmployeeNumber, String> {

  @Override
  public String convertToDatabaseColumn(EmployeeNumber employeeNumber) {
    return employeeNumber == null ? null : employeeNumber.getEmployeeNumber();
  }

  @Override
  public EmployeeNumber convertToEntityAttribute(String employeeNumber) {
    return employeeNumber == null ? null : new EmployeeNumber(employeeNumber);
  }
}