package com.detailretail.kurlyflow.worker.command.domain;

import com.detailretail.kurlyflow.common.exception.BadRequestException;

public class UnAssignedRegionException extends BadRequestException {

  private static final String UN_ASSIGNED = "아직 권역을 배정받지 않았습니다.";

  public UnAssignedRegionException() {
    super(UN_ASSIGNED);
  }
}
