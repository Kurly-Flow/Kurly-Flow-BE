package com.detailretail.kurlyflow.worker.command.application;

import com.detailretail.kurlyflow.common.exception.BadRequestException;

public class LoginFailException extends BadRequestException {

  private static final String LOGIN_FAIL = "로그인에 실패했습니다.";

  public LoginFailException() {
    super(LOGIN_FAIL);
  }
}
