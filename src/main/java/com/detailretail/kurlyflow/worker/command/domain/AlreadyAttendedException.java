package com.detailretail.kurlyflow.worker.command.domain;

import com.detailretail.kurlyflow.common.exception.BadRequestException;

public class AlreadyAttendedException extends BadRequestException {

  private static final String ALREADY_ATTENDED = "이미 출석했습니다.";

  public AlreadyAttendedException() {
    super(ALREADY_ATTENDED);
  }
}
