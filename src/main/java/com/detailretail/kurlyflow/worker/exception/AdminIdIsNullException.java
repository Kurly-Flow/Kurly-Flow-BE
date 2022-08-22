package com.detailretail.kurlyflow.worker.exception;

import com.detailretail.kurlyflow.common.exception.BadRequestException;

public class AdminIdIsNullException extends BadRequestException {

  private static final String ADMIN_ID_IS_NULL = "관리자 ID가 비어있습니다.";


  public AdminIdIsNullException() {
    super(ADMIN_ID_IS_NULL);
  }
}
