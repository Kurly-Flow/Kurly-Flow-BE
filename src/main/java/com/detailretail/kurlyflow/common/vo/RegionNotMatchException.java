package com.detailretail.kurlyflow.common.vo;

import com.detailretail.kurlyflow.common.exception.BadRequestException;

public class RegionNotMatchException extends BadRequestException {

  private static final String REGION_NOT_MATCH = "올바른 권역이 아닙니다.";

  public RegionNotMatchException() {
    super(REGION_NOT_MATCH);
  }
}
