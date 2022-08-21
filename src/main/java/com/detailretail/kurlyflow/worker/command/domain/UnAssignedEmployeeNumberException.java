package com.detailretail.kurlyflow.worker.command.domain;

import com.detailretail.kurlyflow.common.exception.BadRequestException;

public class UnAssignedEmployeeNumberException extends BadRequestException {

  private static final String UN_ASSIGNED_EMPLOYEE_NUMBER = "아직 사번을 입력하지 않았습니다.";

  public UnAssignedEmployeeNumberException() {
    super(UN_ASSIGNED_EMPLOYEE_NUMBER);
  }
}
