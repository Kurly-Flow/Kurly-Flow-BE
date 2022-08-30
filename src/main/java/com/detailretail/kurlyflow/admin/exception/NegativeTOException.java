package com.detailretail.kurlyflow.admin.exception;

import com.detailretail.kurlyflow.common.exception.BadRequestException;

public class NegativeTOException extends BadRequestException {

  private static final String NEGATIVE_TO = "TO에 음수를 입력할 수 없습니다.";

  public NegativeTOException() {
    super(NEGATIVE_TO);
  }
}
