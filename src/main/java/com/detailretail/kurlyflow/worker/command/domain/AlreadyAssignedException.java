package com.detailretail.kurlyflow.worker.command.domain;

import com.detailretail.kurlyflow.common.exception.BadRequestException;

public class AlreadyAssignedException extends BadRequestException {

  private static final String ALREADY_ASSIGNED = "이미 권역을 배정받았습니다.";

  public AlreadyAssignedException() {
    super(ALREADY_ASSIGNED);
  }
}
