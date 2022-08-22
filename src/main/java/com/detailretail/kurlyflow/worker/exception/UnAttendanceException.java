package com.detailretail.kurlyflow.worker.exception;

import com.detailretail.kurlyflow.common.exception.BadRequestException;


public class UnAttendanceException extends BadRequestException {

  private static final String UN_ATTENDANCE = "아직 출근하지 않았습니다.";


  public UnAttendanceException() {
    super(UN_ATTENDANCE);
  }
}
