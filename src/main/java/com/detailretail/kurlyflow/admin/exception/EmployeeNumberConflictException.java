package com.detailretail.kurlyflow.admin.exception;

import com.detailretail.kurlyflow.common.exception.ConflictException;

public class EmployeeNumberConflictException extends ConflictException {
    private static final String EMPLOYEE_NUMBER_CONFLICT = "이미 등록되어 있는 사원번호 입니다.";

  public EmployeeNumberConflictException() {
        super(EMPLOYEE_NUMBER_CONFLICT);
    }
}
