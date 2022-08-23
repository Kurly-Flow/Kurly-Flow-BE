package com.detailretail.kurlyflow.admin.exception;

import com.detailretail.kurlyflow.common.exception.BadRequestException;

public class LackOfWorkingNumbersException extends BadRequestException {

  private static final String LACK_OF_WORKING_NUMBERS = "근무자 수가 부족합니다.";

  public LackOfWorkingNumbersException() {
    super(LACK_OF_WORKING_NUMBERS);
  }
}
