package com.detailretail.kurlyflow.worker.exception;

import com.detailretail.kurlyflow.common.exception.BadRequestException;

public class UnAssignedFieldException extends BadRequestException {

  private static final String UN_ASSIGNED_EMPLOYEE_NUMBER = "아직 사번을 입력하지 않았습니다.";

  public UnAssignedFieldException() {
    super(UN_ASSIGNED_EMPLOYEE_NUMBER);
  }
}
