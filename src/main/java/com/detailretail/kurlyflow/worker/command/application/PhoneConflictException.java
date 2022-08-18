package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.common.exception.ConflictException;

public class PhoneConflictException extends ConflictException {

  private static final String PHONE_CONFLICT = "이미 등록되어 있는 전화번호 입니다.";

  public PhoneConflictException() {
    super(PHONE_CONFLICT);
  }
}
